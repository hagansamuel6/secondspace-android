package com.samapps.hp.a2ndspace.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samapps.hp.a2ndspace.Adapters.HostelDetailAdapter;
import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Hostel;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.SessionConfiguration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HostelDetailFragment extends Fragment {
    private final String TAG = "HostelDetailFragment";
    public final String HOSTEL_NAME = "hostelName";
    private final String HOSTEL_AREA_ID = "hostelAreaId";
    private final String UBER_CLIENT_ID = "odB2108Ow3dT44RJNpVDmWggVJWu5pU6";
    private final String UBER_SEVER_TOKEN = "1z4iwn1oODqMWjKnrn2AvKUFBUZ7_doGFuk5DJmL";

    public  final String SEARCH_HOSTEL_NAME = "search_hostel_name";
    public  final String SEARCH_HOSTEL_AREA_ID = "search_hostel_area_id";

    private String hostelName;
    private String hostelAreaId;
    private String hostelNameForQuery;
    private String hostelIdForQuery;
    String hostelNameFromSearch;
    String hostelIdFromSearch;
    RideRequestButton requestButton;
    private List<Hostel> HostelDetailList;
    HostelDetailFragment hostelDetailFragment;
    Context mContext;

    private Query query;

    //private SwipeRefreshLayout mHostelDetailSwipeRef;

    public HostelDetailAdapter mHostelDetailAdapter;

    TextView mTvHosteldetailName;

    RecyclerView recyclerView;

    DatabaseReference mDb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         String TAG = "HostelDetailFragment";
        View view = inflater.inflate(R.layout.fragment_hostel_detail, container, false);
        mContext = getContext().getApplicationContext();
        requestButton = view.findViewById(R.id.request);
        Bundle hostelListData = getActivity().getIntent().getExtras();

        hostelName = hostelListData.getString(HOSTEL_NAME);
        hostelAreaId = hostelListData.getString(HOSTEL_AREA_ID);

        hostelNameFromSearch = hostelListData.getString(SEARCH_HOSTEL_NAME);
        hostelIdFromSearch = hostelListData.getString(SEARCH_HOSTEL_AREA_ID);

        if (hostelName != null){
            hostelNameForQuery = hostelName;
            hostelIdForQuery = hostelAreaId;
        }else{
            hostelNameForQuery = hostelNameFromSearch;
            hostelIdForQuery = hostelIdFromSearch;
        }

        HostelDetailList = new ArrayList<>();

        mDb = FirebaseDatabase.getInstance().getReference("hostel_areas/"+hostelIdForQuery+"/mHostelsForArea");
        query = mDb.orderByChild("mHostelName").equalTo(hostelNameForQuery).limitToFirst(1);
        query.addValueEventListener(valueEventListener);

        recyclerView = (RecyclerView) view.findViewById(R.id.hostel_detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        mHostelDetailAdapter = new HostelDetailAdapter(getContext().getApplicationContext(), HostelDetailList);
        recyclerView.setAdapter(mHostelDetailAdapter);


        SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId(UBER_CLIENT_ID)
                .setServerToken(UBER_SEVER_TOKEN)
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();

        UberSdk.initialize(config);
        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            HostelDetailList.clear();
            for (DataSnapshot ds: dataSnapshot.getChildren()){
                Hostel hostel = ds.getValue(Hostel.class);
                HostelDetailList.add(hostel);
                Log.d(TAG, ds.toString());
            }

            mHostelDetailAdapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, databaseError.getMessage());
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* mHostelDetailSwipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Doing query", Toast.LENGTH_LONG).show();
                redoQeury();
            }
        });*/
    }


    public void redoQeury(){
        mDb = FirebaseDatabase.getInstance().getReference("hostel_areas/"+hostelIdForQuery+"/mHostelsForArea");
        Query query = mDb.orderByChild("mHostelName").equalTo(hostelNameForQuery).limitToFirst(1);
        query.addValueEventListener(queryEventListener);
    }

    ValueEventListener queryEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            HostelDetailList.clear();
            for (DataSnapshot ds: dataSnapshot.getChildren()){
                Hostel hostel = ds.getValue(Hostel.class);
                HostelDetailList.add(hostel);
                Log.d(TAG, ds.toString());
            }

            mHostelDetailAdapter.notifyDataSetChanged();
            //mHostelDetailSwipeRef.setRefreshing(false);
            makeText("Refresh Complete");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, databaseError.getMessage());
        }
    };


    public void makeText(String msg){
        Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        query.removeEventListener(valueEventListener);
        valueEventListener = null;
        queryEventListener = null;
        mContext = null;
        recyclerView = null;
        mHostelDetailAdapter = null;
    }
}
