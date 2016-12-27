package com.candychat.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.candychat.ui.fragment.FavoriteListFragment;
import com.candychat.ui.fragment.HistoryListFragment;


/**
 * Created by admin on 2016/11/28.
 */

public class HistoryViewPagerAdapter extends FragmentPagerAdapter {

    private HistoryListFragment historyListFragment;
    private FavoriteListFragment favoriteFragment;

    public HistoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            historyListFragment = new HistoryListFragment();
            fragment = historyListFragment;
        }else if(position == 1){
            favoriteFragment = new FavoriteListFragment();
            fragment = favoriteFragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public HistoryListFragment getHistoryListFragment() {
        return historyListFragment;
    }

    public FavoriteListFragment getFavoriteFragment() {
        return favoriteFragment;
    }
}
