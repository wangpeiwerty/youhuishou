package com.eleven.mvp.base;

import android.view.View;

/**
 * Created by Administrator on 2016/11/17.
 */

public interface AdapterItemListener<T> {
    /**
     * 适配器点击回调事件
     * @param data 回调数据
     * @param id 事件Id
     * @param position position
     * @param view 事件View
     */
    void onItemClickListener(T data, int position, int id, View view);



}
