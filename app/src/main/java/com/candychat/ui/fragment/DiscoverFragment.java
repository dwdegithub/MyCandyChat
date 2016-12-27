package com.candychat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candychat.R;
import com.candychat.adapter.DiscoverGridAdapter;
import com.candychat.widget.CustomGridLayoutManager;

/**
 * Created by admin on 2016/12/26.
 */

public class DiscoverFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = "DiscoverFragment";
    private SwipeRefreshLayout swipe_discover;
    private RecyclerView rcv_discover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipe_discover = (SwipeRefreshLayout) view.findViewById(R.id.swipe_discover);
        swipe_discover.setColorSchemeResources(R.color.colorAccent);
        swipe_discover.setOnRefreshListener(this);
        rcv_discover = (RecyclerView) view.findViewById(R.id.rcv_discover);
        CustomGridLayoutManager customGridLayoutManager = new CustomGridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rcv_discover.setLayoutManager(customGridLayoutManager);
        rcv_discover.setAdapter(new DiscoverGridAdapter());
    }

    @Override
    public void onRefresh() {

    }
}
