package com.eleven.mvp.base.lce.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.lang.reflect.Method;
import java.util.Arrays;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/12/22.
 */

public class LcePtrViewPlugin extends LceViewPlugin implements PtrHandler{

    private PtrFrameLayout ptrFrameLayout;
    protected LcePtrViewHandler lcePtrViewHandler;

    public LcePtrViewPlugin(LcePtrViewHandler lcePtrViewHandler, MvpLceView mvpLceView) {
        super(lcePtrViewHandler, mvpLceView);
        this.lcePtrViewHandler = lcePtrViewHandler;
    }

    @Override
    protected void setupBaseView(View view) {
        super.setupBaseView(view);
        ptrFrameLayout = new YjxPtrFrameLayout(baseView.getContext());
        contentView.addView(ptrFrameLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ptrFrameLayout.addView(lcePtrViewHandler.provideContentSubView(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lcePtrViewHandler.setupPtrHeaderAndHandler(ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(this);
        try {
            Method method = findMethod(ptrFrameLayout, "onFinishInflate");
            method.invoke(ptrFrameLayout);
        }catch (Throwable e){
            throw new NullPointerException(e.getMessage());
        }
    }
    @Deprecated
    @Override
    public void setupContentView(View view) {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return lcePtrViewHandler.checkCanDoRefresh(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        mvpLceView.loadData(true);
    }

    public void refreshComplete() {
        ptrFrameLayout.refreshComplete();
    }

    public boolean isRefreshing() {
        return ptrFrameLayout.isRefreshing();
    }

    public void disableWhenHorizontalMove(boolean disable){
        ptrFrameLayout.disableWhenHorizontalMove(disable);
    }

    /**
     * Locates a given method anywhere in the class inheritance hierarchy.
     *
     * @param instance       an object to search the method into.
     * @param name           method name
     * @param parameterTypes method parameter types
     * @return a method object
     * @throws NoSuchMethodException if the method cannot be located
     */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);

                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                return method;
            } catch (NoSuchMethodException e) {
                // ignore and search next
            }
        }

        throw new NoSuchMethodException("Method "
                + name
                + " with parameters "
                + Arrays.asList(parameterTypes)
                + " not found in " + instance.getClass());
    }

    public interface LcePtrViewHandler extends LceViewHandler{
        View provideContentSubView();
        void setupPtrHeaderAndHandler(PtrFrameLayout ptrFrameLayout);
        boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header);
    }
}
