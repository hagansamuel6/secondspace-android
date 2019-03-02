package com.samapps.hp.a2ndspace;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samapps.hp.a2ndspace.fragments.AccountFragment;
import com.samapps.hp.a2ndspace.fragments.ExploreFragment;
import com.samapps.hp.a2ndspace.fragments.SearchFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    final Fragment homeFragment = new ExploreFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment accountFragment = new AccountFragment();
    final FragmentManager fm = getSupportFragmentManager();

    Fragment active = homeFragment;
    BottomNavigationView mBottomNavigation;
    Toolbar mActionBarToolbar;
    Button mAccountUploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.main_container, accountFragment, "3").hide(accountFragment).commit();
        fm.beginTransaction().add(R.id.main_container, searchFragment, "2").hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();

        mBottomNavigation = (BottomNavigationView) findViewById(R.id.bnve);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_menu_item:
                            fm.beginTransaction().hide(active).show(homeFragment).commit();
                            active = homeFragment;
                            return true;

                        case R.id.search_menu_item:
                            fm.beginTransaction().hide(active).show(searchFragment).commit();
                            active = searchFragment;
                            return true;

                        case R.id.account_menu_item:
                            fm.beginTransaction().hide(active).show(accountFragment).commit();
                            active = accountFragment;
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
