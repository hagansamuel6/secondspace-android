package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.AdThumbnail;
import com.samapps.hp.a2ndspace.model.Carousel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselAdAdapter extends RecyclerView.Adapter<CarouselAdAdapter.CarouselAdViewHolder> {
    Context mContext;
    List<AdThumbnail> adThumbnailList;
    public RecyclerView adCarouselRecyclerView;

    public CarouselAdAdapter(Context mContext, List<AdThumbnail> adThumbnailList) {
        this.mContext = mContext.getApplicationContext();
        this.adThumbnailList = adThumbnailList;
    }

    @NonNull
    @Override
    public CarouselAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_explore, parent, false);
        return new CarouselAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselAdViewHolder holder, int position) {
        adCarouselRecyclerView = holder.carouselRecyclerView;
        adCarouselRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        adCarouselRecyclerView.setLayoutManager(layoutManager);

        CarouselAdapter carouselAdapter = new CarouselAdapter(mContext, adThumbnailList.get(position).getmCarousels());
        adCarouselRecyclerView.setAdapter(carouselAdapter);

        carouselAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adThumbnailList.size();
    }

    public class CarouselAdViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView carouselRecyclerView;

        public CarouselAdViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselRecyclerView = itemView.findViewById(R.id.carousel_ad_recycler_view);
        }
    }
}
