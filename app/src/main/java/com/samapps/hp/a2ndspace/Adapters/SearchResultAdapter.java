package com.samapps.hp.a2ndspace.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samapps.hp.a2ndspace.HostelDetailActivity;
import com.samapps.hp.a2ndspace.R;
import com.samapps.hp.a2ndspace.model.HostelIndex;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    Context mContext;
    List<HostelIndex> mHostelIndex;

    public  final String SEARCH_HOSTEL_NAME = "search_hostel_name";
    public  final String SEARCH_HOSTEL_AREA_ID = "search_hostel_area_id";

    public SearchResultAdapter(Context mContext, List<HostelIndex> mHostelIndex) {
        this.mContext = mContext.getApplicationContext();
        this.mHostelIndex = mHostelIndex;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.hostel_search_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.mHostelName.setText(mHostelIndex.get(position).getHostelName());
        holder.mHostelSearchItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mHostelIndex.get(position).getHostelName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, HostelDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(SEARCH_HOSTEL_NAME, mHostelIndex.get(position).getHostelName());
                i.putExtra(SEARCH_HOSTEL_AREA_ID, mHostelIndex.get(position).getHostelAreaId());
                if (isOnline()){
                    mContext.startActivity(i);
                }else {
                    Toast.makeText(mContext, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mHostelIndex.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        TextView mHostelName;
        LinearLayout mHostelSearchItemContainer;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);

            mHostelName = (TextView) itemView.findViewById(R.id.hostel_search_item);
            mHostelSearchItemContainer = (LinearLayout) itemView.findViewById(R.id.hostel_search_item_container);
        }


    }

    public void updateSearchResult(List<HostelIndex> mHostelIndex){
        mHostelIndex = new ArrayList<>();
        mHostelIndex.addAll(mHostelIndex);
        notifyDataSetChanged();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
