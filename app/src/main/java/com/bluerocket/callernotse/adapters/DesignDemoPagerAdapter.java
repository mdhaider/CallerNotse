package com.bluerocket.callernotse.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bluerocket.callernotse.fragments.RecyclerViewFragment;

/**
 * Created by nehal on 11/23/2017.
 */

public class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

    public DesignDemoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        recyclerViewFragment.numberOfItems = getFragmentItemsCount(position);
        return recyclerViewFragment;
    }

    private int getFragmentItemsCount(int pos) {
        return (int) Math.pow(256, (getCount() - pos));
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "itemsCount: " + getFragmentItemsCount(position);
    }
}

