package com.samapps.hp.a2ndspace.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samapps.hp.a2ndspace.Adapters.CarouselAdAdapter;
import com.samapps.hp.a2ndspace.Adapters.ExploreHostelsAdapter;
import com.samapps.hp.a2ndspace.OnlineInterface;
import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.AdThumbnail;
import com.samapps.hp.a2ndspace.model.Hostel;
import com.samapps.hp.a2ndspace.model.HostelArea;
import com.samapps.hp.a2ndspace.model.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ExploreFragment extends Fragment implements OnlineInterface {
    public final String TAG = "ExploreFragment";
    public final String IMAGE_UPLOAD_REFERENCE = "uploads";

    public final String HOSTEL_AREAS = "hostel_areas";
    public final String CAROUSEL_ADS = "carousel_ads";
    public final String HOSTELS = "hostels";

    String mHostelAreaId;
    String mCarouselAdId;

    ExploreHostelsAdapter mHostelsAdapter;
    CarouselAdAdapter carouselAdAdapter;

    DatabaseReference mDatabase, HostelsDatabaseRef, mDbCarouselRef;

    RecyclerView ExploreRecyclerView;
    ProgressBar  mProgressBar;

    Button mButtonOpenSearchPage;
    TextView mTvLoadingExplore, mTvOffline;
    List<HostelArea> mHostelAreas;

    private List<List<Hostel>> listOfLists;

    private List<AdThumbnail> firstRow;
    public List<AdThumbnail> adListingsfromDb;
    private List<Photo> HostelPhotos;
    private List<Hostel> secondRow;
    private List<Hostel> thirdRow;
    private SwipeRefreshLayout mSwipeRefreshView;
    private Button mRetryButton;


    public Context mContext;
    private static final String HOSTEL_INDEX_REFERENCE = "hostels_index";

    public RecyclerView carouselAdRecyclerView;
    int[] sampleImages = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};
    private DatabaseReference HostelIndexDb;


    public ExploreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        //Log.d("ExploreFragment.this", getAdListingsfromDb()+ "from the db", new Exception());
        mContext = getContext().getApplicationContext();

        carouselAdRecyclerView = (RecyclerView) view.findViewById(R.id.carousel_ad_recycler_view);

        ExploreRecyclerView = (RecyclerView) view.findViewById(R.id.hostel_listing_recyclerview);

        mProgressBar = (ProgressBar) view.findViewById(R.id.explore_progressbar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefreshView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_view);
        mSwipeRefreshView.setRefreshing(true);

        mTvLoadingExplore = (TextView) view.findViewById(R.id.tv_loading_explore);

        mTvOffline = (TextView) view.findViewById(R.id.retry_text);
        mTvOffline.setVisibility(View.INVISIBLE);


        if (!isOnline(mContext)) {
            mTvOffline.setVisibility(View.VISIBLE);
            carouselAdRecyclerView.setVisibility(View.INVISIBLE);
            ExploreRecyclerView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mSwipeRefreshView.setRefreshing(false);
            mTvLoadingExplore.setVisibility(View.INVISIBLE);
            makeToast("You're offline, please check your internet and swipe to refresh");
        }






        mHostelAreas = new ArrayList<>();

        ExploreRecyclerView.setHasFixedSize(true);
        ExploreRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mDbCarouselRef = FirebaseDatabase.getInstance().getReference().child(CAROUSEL_ADS);
        mCarouselAdId = mDbCarouselRef.push().getKey();

        firstRow = new ArrayList<>();
        HostelPhotos = new ArrayList<>();
        adListingsfromDb = new ArrayList<>();


        mDbCarouselRef = FirebaseDatabase.getInstance().getReference().child(CAROUSEL_ADS);
        mDbCarouselRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adListingsfromDb.clear();

                for (DataSnapshot adlistings: dataSnapshot.getChildren()) {
                    AdThumbnail adThumbnail = adlistings.getValue(AdThumbnail.class);
                    adListingsfromDb.add(adThumbnail);
                }

                carouselAdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        carouselAdRecyclerView.setHasFixedSize(true);
        carouselAdRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        carouselAdAdapter = new CarouselAdAdapter(mContext, adListingsfromDb);
        carouselAdRecyclerView.setAdapter(carouselAdAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(HOSTEL_AREAS);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mHostelAreas.clear();

                for(DataSnapshot listingsReference: dataSnapshot.getChildren()){
                    HostelArea hostelsArea = listingsReference.getValue(HostelArea.class);
                    Log.d(TAG, "onDataChange: " + hostelsArea);
                    mHostelAreas.add(hostelsArea);
                }

                mHostelsAdapter = new ExploreHostelsAdapter(getContext(), mHostelAreas);
                ExploreRecyclerView.setAdapter(mHostelsAdapter);
                Log.d(TAG, "onDataChange: " + mHostelAreas.size());
                mProgressBar.setVisibility(View.INVISIBLE);
                mTvLoadingExplore.setVisibility(View.INVISIBLE);
                mSwipeRefreshView.setRefreshing(false);
                mHostelsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        Log.d(TAG, adListingsfromDb.size()+" is the size");

        return view;
    }

    public void displayOfflineView(){
            mRetryButton.setVisibility(View.VISIBLE);
            mTvOffline.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mTvLoadingExplore.setVisibility(View.INVISIBLE);

            makeToast("You're offline, please check your internet and swipe to refresh");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()) {
                    setUpListings();
                    setUpCarousel();
                }else {
                    makeToast("Please check your internet connection");
                    mSwipeRefreshView.setRefreshing(false);
                }

            }
        });
    }

    public void setUpListings(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mHostelAreas.clear();
                        for(DataSnapshot listingsReference: dataSnapshot.getChildren()){
                            HostelArea hostelsArea = listingsReference.getValue(HostelArea.class);
                            mHostelAreas.add(hostelsArea);
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mTvLoadingExplore.setVisibility(View.INVISIBLE);
                        ExploreRecyclerView.setVisibility(View.VISIBLE);
                        carouselAdRecyclerView.setVisibility(View.VISIBLE);
                        mHostelsAdapter.updateList(mHostelAreas);
                        mSwipeRefreshView.setRefreshing(false);


                        mTvOffline.setVisibility(View.INVISIBLE);
                        makeToast("Refresh complete");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    public void setUpCarousel(){
        mDbCarouselRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adListingsfromDb.clear();

                for (DataSnapshot adlistings: dataSnapshot.getChildren()) {
                    AdThumbnail adThumbnail = adlistings.getValue(AdThumbnail.class);
                    adListingsfromDb.add(adThumbnail);
                }

                carouselAdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void makeToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        Log.d(TAG, "on start was called");
        super.onStart();
    }


    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
