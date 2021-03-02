package com.situ2001.cliptokindle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.situ2001.cliptokindle.R;
import com.situ2001.cliptokindle.bean.DisplayableList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final DisplayableList mDataSet;

    public RecyclerViewAdapter(DisplayableList mDataSet) {
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getTextView().setText(mDataSet.get(position).getTitle());
        viewHolder.getButton().setOnClickListener(l -> {
            int pos = viewHolder.getAdapterPosition();
            mDataSet.remove(pos);
            this.notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button button;

        public ViewHolder(View v) {
            super(v);
            this.textView = v.findViewById(R.id.text_view);
            this.button = v.findViewById(R.id.button_remove);
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getButton() {
            return button;
        }
    }
}
