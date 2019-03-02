package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Amenities;
import com.samapps.hp.a2ndspace.model.Hostel;
import com.samapps.hp.a2ndspace.model.Photo;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HostelDetailAdapter extends RecyclerView.Adapter<HostelDetailAdapter.HostelDetailsHolder> {

    public Context mContext;
    private WeakReference<Context> weakContext;
    public List<Hostel> mhostel;
    ViewPager viewPager;
    SwipeAdapter swipeAdapter;
    RecyclerView mHostelAmenitiesRecyclerView, mHostelPricingRecyclerView;
    List<Amenities> mAmenities;
    List<Photo> mHostelDetailPhotos;



    int[] sampleApps;

    public HostelDetailAdapter(Context mContext, List<Hostel> mhostel) {
        weakContext = new WeakReference<>(mContext);
        this.mContext = mContext.getApplicationContext();
        this.mhostel = mhostel;
    }

    @NonNull
    @Override
    public HostelDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.hostel_detail_recyclerview_item, parent, false);
        mHostelDetailPhotos = new ArrayList<>();

        return new HostelDetailsHolder(v, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull HostelDetailsHolder holder, int position) {
        Hostel hostel = mhostel.get(position);
        String hostelContact = hostel.getmHostelContact();
        Button callBtn = holder.mCallBtn;
        mHostelDetailPhotos = hostel.getmHostelPhotos();
        callBtn.setEnabled(true);

        holder.carouselView.setPageCount(mHostelDetailPhotos.size());
        holder.carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mHostelDetailPhotos.get(position).getUrl())
                        .error(R.drawable.error)
                        .fit()
                        .centerInside()
                        .placeholder(R.drawable.loading)
                        .into(imageView);
            }
        });

        if (hostelContact.equals("")){
            callBtn.setEnabled(false);
        }

        holder.mHostelContact.setText(hostel.getmHostelContact());
        holder.mHostelDetailName.setText(hostel.getmHostelName());
        holder.mHostelDetailDescription.setText(hostel.getmHostelDescription());
        holder.mHostelLocation.setText(hostel.getmHostelLocation());

        if (hostel.getmHostelAvailability().equals("none")){
            Drawable img = mContext.getApplicationContext().getResources().getDrawable( R.drawable.ic_space_not_available );
            img.setBounds( 0, 0, 60, 60 );
            holder.mHostelSpaceAvailability.setText("Booking has ended for this hostel (further enqueries))");
            holder.mHostelSpaceAvailability.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }else {
            holder.mHostelSpaceAvailability.setText(hostel.getmHostelAvailability());
        }

        holder.mBtnDetailDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+hostel.getmHostelName());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setData(mIntentUri);

                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null){
                    mContext.startActivity(mapIntent);
                }
            }
        });

        holder.mCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!callBtn.isEnabled()){
                    makeToast("this hostel has no Contact");
                    callBtn.setText("hostel has Contact");
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:"+hostel.getmHostelContact()));
               mContext.startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mHostelAmenitiesRecyclerView = holder.mHostelAmenities;
        mHostelAmenitiesRecyclerView.setHasFixedSize(true);
        mHostelAmenitiesRecyclerView.setFocusable(false);
        mHostelAmenitiesRecyclerView.setLayoutManager(linearLayoutManager);
        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(mContext, mhostel.get(position).getmHostelAmenities());
        mHostelAmenitiesRecyclerView.setAdapter(amenitiesAdapter);
        amenitiesAdapter.notifyDataSetChanged();

        HostelPricingAdapter hostelPricingAdapter = new HostelPricingAdapter(mContext, mhostel.get(position).getmHostelPricing());
        mHostelPricingRecyclerView = holder.mHostelPricingRecyclerView;
        mHostelPricingRecyclerView.setHasFixedSize(true);
        mHostelPricingRecyclerView.setFocusable(false);
        LinearLayoutManager pricingLinearLayoutManager = new LinearLayoutManager(mContext);
        mHostelPricingRecyclerView.setLayoutManager(pricingLinearLayoutManager);
        mHostelPricingRecyclerView.setAdapter(hostelPricingAdapter);
        hostelPricingAdapter.notifyDataSetChanged();

        String HostelImage = this.mhostel.get(position).getmHostelImage();

    }

    private void makeToast(String msg) {
        Toast.makeText(weakContext.get(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return mhostel.size();
    }

    public class HostelDetailsHolder extends RecyclerView.ViewHolder {
        public int mPosition;
        private TextView mHostelDetailName, mHostelDetailDescription,
                mHostelLocation, mHostelContact, mHostelSpaceAvailability;
        public CarouselView mHostelDetailImageView;
        public Button mBtnDetailDirection, mCallBtn;
        RecyclerView mHostelAmenities, mHostelPricingRecyclerView;
        CarouselView carouselView;

        public HostelDetailsHolder(@NonNull View itemView, int position) {
            super(itemView);
            mPosition = position;
            mHostelDetailName = (TextView) itemView.findViewById(R.id.tv_hostel_detail_name);
            carouselView = (CarouselView) itemView.findViewById(R.id.hostel_image_pager);
            mHostelDetailDescription = (TextView) itemView.findViewById(R.id.tv_hostel_detail_description);
            mHostelContact = (TextView) itemView.findViewById(R.id.tv_hostel_contact);
            mHostelLocation = (TextView) itemView.findViewById(R.id.tv_detail_location);
            mHostelSpaceAvailability = (TextView) itemView.findViewById(R.id.tv_hostel_availability);
            mBtnDetailDirection = (Button) itemView.findViewById(R.id.btn_detail_directions);
            mHostelAmenities = (RecyclerView) itemView.findViewById(R.id.hostel_amenities_recycler_view);
            mHostelPricingRecyclerView = (RecyclerView) itemView.findViewById(R.id.hostel_pricing_recyclerView);
            mCallBtn = (Button) itemView.findViewById(R.id.call_button);

        }
    }
}
