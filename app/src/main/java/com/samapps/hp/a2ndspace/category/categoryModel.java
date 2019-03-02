package com.samapps.hp.a2ndspace.category;


import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import androidx.annotation.NonNull;

public class categoryModel implements ViewModel {

    @NonNull
    private final String mTitle;

    public categoryModel(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    @NonNull
    public String getName(){
        return mTitle;
    }

    @Override
    public int hashCode(){
        return mTitle.hashCode();
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "{" + mTitle + "}";
    }
}
