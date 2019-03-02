package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SwipeAdapter extends PagerAdapter {
    List<Photo> mhostelPhotos;
    Context mCtx;
    LayoutInflater layoutInflater;

    public SwipeAdapter(List<Photo> mhostelPhotos, Context context) {
        this.mCtx = context.getApplicationContext();
        this.mhostelPhotos = mhostelPhotos;
    }

    @Override
    public int getCount() {
        return mhostelPhotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.image_slider, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_in_slider);

        Picasso.get().load(mhostelPhotos.get(position).getUrl())
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView((LinearLayout) object);
    }
}
