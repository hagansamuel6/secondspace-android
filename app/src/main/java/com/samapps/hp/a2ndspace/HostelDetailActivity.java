package com.samapps.hp.a2ndspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.samapps.hp.a2ndspace.fragments.HostelDetailFragment;

public class HostelDetailActivity extends AppCompatActivity {
    Fragment HostelDetailFragment;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FrameLayout HostelDetailFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_detail);

        HostelDetailFragment = new HostelDetailFragment();

        fragmentManager.beginTransaction().add(R.id.hostel_detail_fragment_container, HostelDetailFragment, "1").commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HostelDetailFragment = null;
        fragmentManager = null;
    }
}
