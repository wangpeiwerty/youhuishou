package com.eleven.mvp.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SimpleViewHolder extends RecyclerView.ViewHolder {


    private SparseArray<View> mViews;

    private int type;

    public SimpleViewHolder(View itemView) {
        this(itemView, 0);
    }

    public SimpleViewHolder(View itemView, int type) {
        super(itemView);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int id){

        if(mViews == null)
            mViews = new SparseArray<>();

        View view = mViews.get(id);
        if(view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }

        return (V) view;

    }

    public <V extends View> V getView(int id, Class<V> vClass){
        return (V)getView(id);
    }
}
