package com.samapps.hp.a2ndspace.Adapters;

import com.samapps.hp.a2ndspace.fragments.DetailOverViewFragment;
import com.samapps.hp.a2ndspace.fragments.DetailPhotosFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DetailOverViewFragment detailOverViewFragment = new DetailOverViewFragment();
                return detailOverViewFragment;
            case 1:
                DetailPhotosFragment detailPhotosFragment = new DetailPhotosFragment();
                return detailPhotosFragment;
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
