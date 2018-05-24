package com.eleven.mvp.base.lce.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eleven.mvp.R;
import com.eleven.mvp.base.SimpleViewHolder;
import com.eleven.mvp.base.lce.LcePtrUlmView;
import com.eleven.mvp.base.lce.PageManager;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.Collection;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/12/22.
 */

public class LcePtrUlmViewPlugin<M> extends LcePtrViewPlugin implements Paginate.Callbacks{

    private boolean isMoreLoading;
    protected RecyclerView recyclerView;
    private PageManager pageManager;
    private Paginate paginate;
    private boolean hasLoadedAllItems = true;
    private LcePtrUlmViewHandler lcePtrUlmViewHandler;
    private LcePtrUlmView lcePtrUlmView;

    public LcePtrUlmViewPlugin(LcePtrUlmViewHandler lcePtrUlmViewHandler, LcePtrUlmView lcePtrUlmView){
        super(lcePtrUlmViewHandler, lcePtrUlmView);
        this.lcePtrUlmViewHandler = lcePtrUlmViewHandler;
        this.lcePtrUlmView = lcePtrUlmView;
    }

    @Override
    protected void setupBaseView(View view) {
        super.setupBaseView(view);
        recyclerView = lcePtrUlmViewHandler.generateRecyclerViewAndBindAdapter(view);
        if(recyclerView == null)
            throw new NullPointerException("Recycler view is null");
        initPaginate();
        generatePageManager();
    }

    @Override
    public boolean isLoading() {
        // 正在刷新时不能加载更多
        return isMoreLoading || isRefreshing();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        if(recyclerView == null)
            return false;
        return super.checkCanDoRefresh(frame, recyclerView, header) && !isLoading();
    }
    @Override
    public void onLoadMore() {
        isMoreLoading = true;
        lcePtrUlmViewHandler.onLoadMore();
    }

    public void loadMoreComplete(boolean isLoadSuccess) {
        if(!isLoadSuccess) {
            View view = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            if(view != null)
                recyclerView.scrollBy(0, -view.getHeight());
        }
        isMoreLoading = false;
    }

    private void initPaginate(){
        paginate = Paginate.with(recyclerView, this)
                .addLoadingListItem(true)
                .setLoadingTriggerThreshold(0)
                .setLoadingListItemCreator(new LoadingListItemCreator() {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore_layout, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                    }
                }).build();
        paginate.setHasMoreDataToLoad(false);
    }

    private void generatePageManager(){
        pageManager = new PageManager.Builder().with(recyclerView).setLimit(lcePtrUlmView.getLimit()).build();
    }

    public int getPage() {
        return pageManager.getCurrentPage();
    }

    public void addData(M data) {
        hasLoadedAllItems = unequalLimitSize(data);

        if(!hasLoadedAllItems) {

            pageManager.next();
        }
    }

    public void setData(M data) {
        hasLoadedAllItems = unequalLimitSize(data);

        if(data != null) {
            pageManager.refresh();
        }
    }

    private boolean unequalLimitSize(M data){
        if(data == null) {
            return true;
        }
        if(data instanceof Collection && ((Collection)data).size() < lcePtrUlmView.getLimit()){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return hasLoadedAllItems;
    }

    public interface LcePtrUlmViewHandler extends LcePtrViewHandler{
        RecyclerView generateRecyclerViewAndBindAdapter(View view);
        void onLoadMore();

    }
}
