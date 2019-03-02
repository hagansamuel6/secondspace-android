package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Amenities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.AmenitiesViewHolder> {

    public Context mContext;
    public List<Amenities> mAmenities;

    public AmenitiesAdapter(Context mContext, List<Amenities> mAmenities) {
        this.mContext = mContext.getApplicationContext();
        this.mAmenities = mAmenities;
    }

    @NonNull
    @Override
    public AmenitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_amenity, parent, false);
        return new AmenitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesViewHolder holder, int position) {
        holder.tvAmenityName.setText(mAmenities.get(position).getNameOfAmenety());
    }

    @Override
    public int getItemCount() {
        return mAmenities.size();
    }

    public class AmenitiesViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmenityName;
        public AmenitiesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAmenityName = (TextView) itemView.findViewById(R.id.tv_amenity_name);
        }
    }
}
