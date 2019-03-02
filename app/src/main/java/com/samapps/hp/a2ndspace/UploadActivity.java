package com.samapps.hp.a2ndspace;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadActivity extends AppCompatActivity {
    public final String IMAGE_UPLOAD_REFERENCE = "hostel/thumbnails";
    public final String HOSTEL_AREAS = "hostel_areas";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "UploadActivity.this";
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextHostelArea;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private StorageTask mUploadTask;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mHostelLocationsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        mButtonUpload = (Button) findViewById(R.id.upload_image_button);
        mButtonChooseImage = (Button) findViewById(R.id.select_image_button);
        mTextViewShowUploads = (TextView) findViewById(R.id.text_view_show_uploads);
        mEditTextHostelArea = (EditText) findViewById(R.id.edit_text_file_name);
        //mImageView = (ImageView) findViewById(R.id.image_view);

        mImageView = (ImageView) findViewById(R.id.upload_image_preview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference(IMAGE_UPLOAD_REFERENCE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(IMAGE_UPLOAD_REFERENCE);
        mHostelLocationsRef = FirebaseDatabase.getInstance().getReference(HOSTEL_AREAS);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void createHostelArea(){
        Toast.makeText(UploadActivity.this, "Hostel Area Created Successfully", Toast.LENGTH_LONG).show();
    }

    private void uploadFile() {
        if (mImageUri != null){
                mStorageRef.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {

                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        mProgressBar.setVisibility(View.VISIBLE);
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return mStorageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            Uri downloadUri = task.getResult();
                            Log.e(TAG, "then: " + downloadUri.toString());

                            Upload upload = new Upload(mEditTextHostelArea.getText().toString().trim(),
                                    downloadUri.toString());
                                    mDatabaseRef.push().setValue(upload);
                                    mProgressBar.setVisibility(View.INVISIBLE);
                        } else
                        {
                            Toast.makeText(UploadActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
}

