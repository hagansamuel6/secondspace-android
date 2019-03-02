package com.samapps.hp.a2ndspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.samapps.hp.a2ndspace.model.Hostel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadHostelArea extends AppCompatActivity {
    public final String IMAGE_UPLOAD_REFERENCE = "hostel/thumbnails/thumbnail" + System.currentTimeMillis();
    public final String HOSTELS_REFERENCE = "hostels";
    private final String TAG = "UploadHostelArea.this";

    public final String HOSTEL_AREAS = "hostel_areas";
    private StorageReference mStorageRef;
    private DatabaseReference mHostelsRef;


    private Uri mImageUri;

    EditText mEditTextHostelArea;
    EditText mEditTextHostelName;
    EditText mEditTextHostelLocation;
    Button mSelectHostelButton;
    Button mUploadHostelDetailButton;
    ImageView mHostelThumbnail;


    Button mUploadHostelSection;
    private DatabaseReference mHostelLocationsRef;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_hostel_area);
        mEditTextHostelArea = (EditText) findViewById(R.id.edit_text_hostel_area);


        mHostelLocationsRef = FirebaseDatabase.getInstance().getReference(HOSTEL_AREAS);
        mHostelsRef = FirebaseDatabase.getInstance().getReference(HOSTELS_REFERENCE);

        mUploadHostelSection = (Button) findViewById(R.id.button_hostel_section);

        mEditTextHostelName = (EditText) findViewById(R.id.hostel_name_edit_text);
        mEditTextHostelLocation = (EditText) findViewById(R.id.hostel_location_edit_text);

        mSelectHostelButton = (Button) findViewById(R.id.select_image_button);
        mUploadHostelDetailButton = (Button) findViewById(R.id.upload_image_button);

        mHostelThumbnail = (ImageView) findViewById(R.id.upload_image_preview);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        mSelectHostelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mUploadHostelDetailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        mUploadHostelSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHostelArea();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mHostelThumbnail);
        }
    }

    private void createHostelArea(){
        Toast.makeText(UploadHostelArea.this, "Hostel Area Created Successfully", Toast.LENGTH_LONG).show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        if (mImageUri != null){
            StorageReference fileReference = mStorageRef.child("Thumbnails/" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    Toast.makeText(UploadHostelArea.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                    Toast.makeText(UploadHostelArea.this, "hostel upload success", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UploadHostelArea.this, "upload successful ", Toast.LENGTH_SHORT).show();

                    } else
                    {
                        Toast.makeText(UploadHostelArea.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
