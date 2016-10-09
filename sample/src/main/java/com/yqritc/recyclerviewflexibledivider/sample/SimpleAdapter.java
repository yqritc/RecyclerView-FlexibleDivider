package com.yqritc.recyclerviewflexibledivider.sample;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yqritc on 2015/01/08.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    public SimpleAdapter(Context context) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int rId = R.layout.layout_sample_item;
        if (((RecyclerView)parent).getLayoutManager() instanceof LinearLayoutManager)
        {
            LinearLayoutManager manager = (LinearLayoutManager)((RecyclerView)parent).getLayoutManager();
            if (manager.getOrientation() == LinearLayoutManager.HORIZONTAL)
            {
                rId = R.layout.layout_sample_item2;
            }
        }
        else if (((RecyclerView)parent).getLayoutManager() instanceof GridLayoutManager)
        {
            GridLayoutManager manager = (GridLayoutManager)((RecyclerView)parent).getLayoutManager();
            if (manager.getOrientation() == GridLayoutManager.HORIZONTAL)
            {
                rId = R.layout.layout_sample_item2;
            }
        }
        View view = mLayoutInflater.inflate(rId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSampleText.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 32;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mSampleText;

        public ViewHolder(View view) {
            super(view);
            mSampleText = (TextView) view.findViewById(R.id.text_sample);
        }
    }
}
