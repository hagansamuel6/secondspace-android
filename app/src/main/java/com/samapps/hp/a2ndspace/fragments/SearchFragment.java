package com.samapps.hp.a2ndspace.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.samapps.hp.a2ndspace.Adapters.SearchResultAdapter;
import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.Hostel;
import com.samapps.hp.a2ndspace.model.HostelIndex;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private  final String TAG = "SearchFragment";
    private  final String HOSTEL_INDEX_REFERENCE = "hostels_index";
    public  final String SEARCH_HOSTEL_NAME = "search_hostel_name";
    public  final String SEARCH_HOSTEL_AREA_ID = "search_hostel_area_id";
    private DatabaseReference mDb, HostelIndexDb;


    List<SearchSuggestion> newSuggestions;


    public SearchFragment() {
    }

    MaterialSearchView mSearchView;
    private RecyclerView mSearchSuggestionRecylerview;
    private List<HostelIndex> mHostelIndex;
    private SearchResultAdapter mSearchResultAdapter;
    private ProgressBar mHostelIndexProgress;
    TextView mSearchViewHelp;
    String nameFromResult;
    String IdFromResult;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Toolbar toolbar = view.findViewById(R.id.search_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.requestFocus();
        setHasOptionsMenu(true);

        mHostelIndex = new ArrayList<>();

        //mHostelIndex.add(new HostelIndex("-LXK9Vy5coJIWjpOiLWo", "Dr Sarfo Hostel"));
        mHostelIndexProgress = (ProgressBar) view.findViewById(R.id.hostel_indicator_progress);

        mSearchSuggestionRecylerview = view.findViewById(R.id.search_result_recyclerView);
        mSearchSuggestionRecylerview.setHasFixedSize(true);

        mSearchViewHelp = (TextView) view.findViewById(R.id.search_help_hint);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        mSearchSuggestionRecylerview.setLayoutManager(linearLayoutManager);





        mDb = FirebaseDatabase.getInstance().getReference("hostel_areas/-LXK9Vy5coJIWjpOiLWo/mHostelsForArea");
        Query query = mDb.orderByChild("mHostelName").equalTo("Westend Hostel").limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //HostelDetailList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Hostel hostel = ds.getValue(Hostel.class);
                    Log.d(TAG, ds.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
        mSearchViewHelp.setVisibility(View.VISIBLE);

        HostelIndexDb = FirebaseDatabase.getInstance()
                .getReference(HOSTEL_INDEX_REFERENCE);


        Log.d(TAG, "onCreateView()");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchViewHelp.setVisibility(View.VISIBLE);
        Log.d(TAG, "onViewCreated()");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.requestFocus();
        searchView.setQueryHint("Search by hostel name");
        searchView.setOnQueryTextListener(this);
    }


    ValueEventListener hostelIndexValueIndexListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mHostelIndex.clear();

            for (DataSnapshot hostelIndexSnapshot: dataSnapshot.getChildren()){
                HostelIndex hostelIndex = hostelIndexSnapshot.getValue(HostelIndex.class);
                mHostelIndex.add(hostelIndex);
            }

            HostelIndexDb.addValueEventListener(hostelIndexValueIndexListener);
            //mSearchResultAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            makeToast("please check your internet connection " + databaseError.getMessage());
        }
    };

    //this is called when the text is submitted
    @Override
    public boolean onQueryTextSubmit(String query) {
        mHostelIndex.clear();
        makeToast("Please Select a Suggestion");
        HostelIndexDb.removeEventListener(hostelIndexValueIndexListener);
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        String userQuery = newText.toLowerCase();
        List<HostelIndex> newList = new ArrayList<>();

        mHostelIndexProgress.setVisibility(View.VISIBLE);
        HostelIndexDb.addValueEventListener(hostelIndexValueIndexListener);

        for (HostelIndex hostelIndex : mHostelIndex){
            nameFromResult = hostelIndex.getHostelName();
            IdFromResult = hostelIndex.getHostelAreaId();
            String name = hostelIndex.getHostelName().trim().toLowerCase();
            //name = name.substring(lastIndex, name.length());
            name = name.substring(0, name.lastIndexOf(" "));

            Log.d(TAG, "onQueryTextChange: " + name);

            if (name.contains(userQuery)){
                newList.add(hostelIndex);
                mHostelIndexProgress.setVisibility(View.INVISIBLE);
            }
        }

        if (newText.equals("")){
            newList.clear();
            mSearchViewHelp.setVisibility(View.VISIBLE);
            mHostelIndexProgress.setVisibility(View.INVISIBLE);
            HostelIndexDb.removeEventListener(hostelIndexValueIndexListener);
        }

        mSearchViewHelp.setVisibility(View.INVISIBLE);
        SearchResultAdapter mSearchResultAdapter = new SearchResultAdapter(getContext().getApplicationContext(), newList);
        mSearchSuggestionRecylerview.setAdapter(mSearchResultAdapter);


        //mSearchResultAdapter.updateSearchResult(newList);
       //mSearchSuggestionRecylerview.setAdapter(mSearchResultAdapter);
        //mSearchResultAdapter.notifyDataSetChanged();
        //newList.clear();
        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*   @Override
    public void OnSearchResultclick(int position) {

        *//*Intent i = new Intent(getActivity(), HostelDetailActivity.class);
        i.putExtra(SEARCH_HOSTEL_NAME, nameFromResult);
        i.putExtra(SEARCH_HOSTEL_AREA_ID, IdFromResult);*//*

        makeToast(""+mHostelIndex.get(position).getHostelName());
        //startActivity(i);
    }*/
}
