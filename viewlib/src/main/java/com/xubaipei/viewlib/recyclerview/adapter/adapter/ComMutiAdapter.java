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
public abstract class ComMutiAdapter<T> extends RecyclerView.Adapter<ComHolder> {
    private Context mContext;
    private List<T> mData;
    private int mLayoutId;

    public ComMutiAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<T>();
    }
    public void addData(List<T> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    public abstract int getViewHolderLayoutId(int position,List<T> mData);

    @Override
    public int getItemViewType(int position) {
        return getViewHolderLayoutId(position,mData);
    }

    @Override
    public ComHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComHolder(mContext,parent,viewType);
    }

    @Override
    public void onBindViewHolder(ComHolder holder, int position) {
        mLayoutId = getViewHolderLayoutId(position,mData);
        conver(holder,mData.get(position),mLayoutId);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public abstract void conver(ComHolder holder, T t,int layoutId);
}
