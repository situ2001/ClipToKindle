package com.situ2001.cliptokindle.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.situ2001.cliptokindle.R
import com.situ2001.cliptokindle.adapter.TextRecyclerViewAdapter
import com.situ2001.cliptokindle.text.Text
import com.situ2001.cliptokindle.text.TextSetHelper

class RecyclerViewFragment : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    lateinit var adapter: TextRecyclerViewAdapter
    private lateinit var mDataset: MutableList<Text>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataset = TextSetHelper.getList() //initialize the dataset
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        rootView.tag = TAG
        mRecyclerView = rootView.findViewById(R.id.recycleView)

        //set LayoutManager
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        //set Adapter
        adapter = TextRecyclerViewAdapter(requireContext(), mDataset)
        mRecyclerView.adapter = adapter
        return rootView
    }

    companion object {
        private const val TAG: String = "RecyclerViewFragment"
    }
}