package com.eleven.mvp.base.lce;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.eleven.mvp.R;


/**
 * Created by Robert on 2016/11/24.
 */
public class ToolbarManager {


    private Toolbar toolBar;


    public static ToolbarManager with(AppCompatActivity appcompatActivity){
        Toolbar toolbar = (Toolbar) appcompatActivity.findViewById(R.id.toolbar);
        setUpToolBar(appcompatActivity, toolbar);
        return new ToolbarManager(toolbar);
    }

    public  Toolbar getToolBar(){
        return toolBar;
    }


    private static void setUpToolBar(AppCompatActivity appCompatActivity, Toolbar toolbar) {
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
    }

    public ToolbarManager(Toolbar toolBar) {
        if (null == toolBar){
            throw new IllegalArgumentException("You must set toolbar instance");
        }
        this.toolBar = toolBar;
    }

    public ToolbarManager title(String s) {
        TextView textView = (TextView) toolBar.findViewById(R.id.title_content_tv);
        textView.setText(s);
        return this;
    }
    public ToolbarManager titleColor(int resource_color) {
        toolBar.setBackgroundColor(resource_color);
        return this;
    }

    public ToolbarManager setNavigationIcon(int res, View.OnClickListener onClickListener){
        toolBar.setNavigationIcon(res);
        toolBar.setNavigationOnClickListener(onClickListener);

        return this;
    }
}
