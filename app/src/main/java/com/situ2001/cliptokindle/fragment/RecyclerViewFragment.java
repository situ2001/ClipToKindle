package com.situ2001.cliptokindle.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.situ2001.cliptokindle.R;
import com.situ2001.cliptokindle.adapter.RecyclerViewAdapter;
import com.situ2001.cliptokindle.bean.DisplayableList;
import com.situ2001.cliptokindle.bean.SingletonDisplayableList;

public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected RecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected DisplayableList mDataset;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mDataset = TextSetHelper.getList(); //initialize the dataset
        mDataset = SingletonDisplayableList.getSingleton();
    }

    public RecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.recycle_view);

        //set LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //set Adapter
        mAdapter = new RecyclerViewAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}