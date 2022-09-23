package com.example.finalproject;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numTabs){
        super(fragmentManager);
        this.numTabs = numTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new PlayerFragment();
            case 1:
                return new TeamsFragment();
            case 2:
                return new LeaguesFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
