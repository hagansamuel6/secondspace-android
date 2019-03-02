package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.samapps.hp.a2ndspace.BitmapTransform;
import com.samapps.hp.a2ndspace.HostelDetailActivity;
import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Hostel;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class HostelsListingsAdapter extends RecyclerView.Adapter<HostelsListingsAdapter.HostelsListingsViewHolder> {

    public final String HOSTEL_NAME = "hostelName";
    private final String HOSTEL_AREA_ID = "hostelAreaId";
    List<Hostel> hostels;
    Context context;
    private OnItemClickListener itemClickListener;
    private final String TAG = "ListingsAdapter";
    private DatabaseReference mDb;
    private String HostelAreaId;
    private String HostelImage;

    public HostelsListingsAdapter( Context context, List<Hostel> hostels) {
        this.context = context.getApplicationContext();
        this.hostels = hostels;

    }

    @NonNull
    @Override
    public HostelsListingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hostel_item, parent, false);
        return  new HostelsListingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelsListingsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position + " " + hostels.get(position).getmHostelImage());
        HostelImage = hostels.get(position).getmHostelImage();

        final int MAX_WIDTH = 1024;
        final int MAX_HEIGHT = 768;


        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        Picasso.get().load(HostelImage)
                .error(R.drawable.error)
                .centerInside()
                .fit()
                .placeholder(R.drawable.loading)
                .into(holder.hostelThumbnail);

        Hostel hostel = hostels.get(position);
        holder.hostelName.setText(hostel.getmHostelName());

        HostelAreaId = hostel.getmHostelAreaId();

        holder.singleHostelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HostelDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(HOSTEL_NAME, hostel.getmHostelName());
                intent.putExtra(HOSTEL_AREA_ID, HostelAreaId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + hostels.size());
        return hostels.size();
    }


    public class HostelsListingsViewHolder extends RecyclerView.ViewHolder {
         TextView hostelName;
         ImageView hostelThumbnail;
         TextView hostelLocation;
         CardView singleHostelItem;

        public HostelsListingsViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = (TextView) itemView.findViewById(R.id.hostel_name);
            singleHostelItem = (CardView) itemView.findViewById(R.id.single_hostel_item);
            hostelThumbnail = (ImageView) itemView.findViewById(R.id.hostel_thumbnail);
            //itemView.setOnClickListener(this);
        }

    }

}
