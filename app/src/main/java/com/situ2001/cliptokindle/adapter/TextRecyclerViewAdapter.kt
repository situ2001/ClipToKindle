package com.situ2001.cliptokindle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.situ2001.cliptokindle.R
import com.situ2001.cliptokindle.text.Text
import com.situ2001.cliptokindle.text.TextSetHelper

class TextRecyclerViewAdapter(private val context: Context, private val mDataSet: MutableList<Text>) : RecyclerView.Adapter<TextRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.text_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = mDataSet[position].text
        viewHolder.button.setOnClickListener {
            val pos = viewHolder.adapterPosition
            TextSetHelper.get().remove(pos)
            notifyItemRemoved(pos)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val button: Button = view.findViewById(R.id.button_remove)
    }

    companion object {
        private val TAG: String = "CustomAdapter"
    }
}