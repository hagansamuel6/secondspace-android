package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.HostelArea;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ExploreHostelsAdapter extends RecyclerView.Adapter<ExploreHostelsAdapter.HostelsImageHolder> {
    private final String TAG = "ExploreHostels";
   private Context mContext;
   private List<HostelArea> hostelAreas;

    public ExploreHostelsAdapter(Context mContext, List<HostelArea> hostels) {
        this.mContext = mContext.getApplicationContext();
        this.hostelAreas = hostels;
    }


    @NonNull
    @Override
    public HostelsImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.hostel_section_container, viewGroup, false);
        return new HostelsImageHolder(v, i);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelsImageHolder hostelsImageHolder, int i) {
        Log.d(TAG, i + " onBindViewHolder called");
        hostelsImageHolder.mHostelAreaName.setText(hostelAreas.get(i).getmName());

        LinearLayoutManager mlinearLayoutManager;
        mlinearLayoutManager = new LinearLayoutManager(mContext);
        mlinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        HostelsListingsAdapter hostelsListingsAdapter;
        Log.d(TAG, "onBindViewHolder: hostel size" + hostelAreas.get(i).getmHostelsForArea().size());
        hostelsListingsAdapter = new HostelsListingsAdapter(mContext, hostelAreas.get(i).getmHostelsForArea());
        hostelsImageHolder.mHostelAreaListingsRecyclerView.setAdapter(hostelsListingsAdapter);
        hostelsImageHolder.mHostelAreaListingsRecyclerView.setNestedScrollingEnabled(false);
        hostelsImageHolder.mHostelAreaListingsRecyclerView.setLayoutManager(mlinearLayoutManager);
        hostelsImageHolder.mHostelAreaListingsRecyclerView.setHasFixedSize(true);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return hostelAreas.size();
    }

    public class HostelsImageHolder extends RecyclerView.ViewHolder {
        public int mposition;
        public TextView mHostelAreaName;
        private RecyclerView mHostelAreaListingsRecyclerView;

        public HostelsImageHolder(@NonNull View itemView, Integer position) {
            super(itemView);
            mposition = position;

            mHostelAreaName = (TextView)itemView.findViewById(R.id.hostel_name_text_view);
            mHostelAreaListingsRecyclerView = itemView.findViewById(R.id.hostels_horizontal_recyclerview);

        }

        public void giveShoutOut(){
            Toast.makeText(mContext, "OnBindViewHolder called at 5", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateList(List<HostelArea> Hostel){
        hostelAreas = new ArrayList<>();
        hostelAreas.addAll(Hostel);
        notifyDataSetChanged();
    }
}
