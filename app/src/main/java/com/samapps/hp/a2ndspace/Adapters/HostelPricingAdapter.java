package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.HostelPricing;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HostelPricingAdapter extends RecyclerView.Adapter<HostelPricingAdapter.HostelPricingViewHolder> {
     Context mContext;
     List<HostelPricing> mHostelPricingList;

    public HostelPricingAdapter(Context mContext, List<HostelPricing> mHostelPricing) {
        this.mContext = mContext.getApplicationContext();
        this.mHostelPricingList = mHostelPricing;
    }

    @NonNull
    @Override
    public HostelPricingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pricing, parent, false);
        return new HostelPricingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelPricingViewHolder holder, int position) {
        holder.mHostelPriceTv.setText(mHostelPricingList.get(position).getHostelRoomPrice());
        holder.mRoomTypeTv.setText(mHostelPricingList.get(position).getHostelRoomType());
    }

    @Override
    public int getItemCount() {
        return mHostelPricingList.size();
    }

    public class HostelPricingViewHolder extends RecyclerView.ViewHolder {
        private TextView mHostelPriceTv;
        private TextView mRoomTypeTv;
        public HostelPricingViewHolder(@NonNull View itemView) {
            super(itemView);

            mHostelPriceTv = (TextView) itemView.findViewById(R.id.tv_pricing);
            mRoomTypeTv = (TextView) itemView.findViewById(R.id.tv_room_type);
        }
    }
}
