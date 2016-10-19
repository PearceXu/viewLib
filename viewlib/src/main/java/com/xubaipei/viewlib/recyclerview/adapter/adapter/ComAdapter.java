package com.xubaipei.viewlib.recyclerview.adapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xubaipei.viewlib.recyclerview.adapter.viewHolder.ComHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubaipei on 2016/9/19.
 */
public abstract class ComAdapter<T> extends RecyclerView.Adapter<ComHolder> {
    private Context mContext;
    private List<T> mData;
    int mLayoutId;

    public ComAdapter(Context mContext, int layoutId) {
        this.mContext = mContext;
        mData = new ArrayList<T>();
        mLayoutId = layoutId;
    }
    public void addData(List<T> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
    public void loadMore(List<T> list){
        mData.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public ComHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComHolder(mContext,parent,mLayoutId);
    }

    @Override
    public void onBindViewHolder(ComHolder holder, int position) {
        conver(holder,mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public abstract void conver(ComHolder holder, T bean);
}
