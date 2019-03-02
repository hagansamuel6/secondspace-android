package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Carousel;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    Context mContext;
    List<Carousel> mCarousel;

    public CarouselAdapter(Context mContext, List<Carousel> mCarousel) {
        this.mContext = mContext.getApplicationContext();
        this.mCarousel = mCarousel;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.carousel_recycler_view_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.carouselView.setPageCount(mCarousel.size());
        holder.carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mCarousel.get(position).getUrl())
                        .error(R.drawable.error)
                        .placeholder(R.drawable.loading)
                        .into(imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarousel.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        CarouselView carouselView;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselView = (CarouselView) itemView.findViewById(R.id.carousel_ad_imageview);
        }
    }
}
