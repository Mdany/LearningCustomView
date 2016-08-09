package com.chenyu.monster.customviewtest.view.pullToRefresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import com.chenyu.monster.customviewtest.DisplayUtils;

/**
 * Created by chenyu on 16/8/9.
 */
public abstract class BaseRefreshView extends Drawable implements Drawable.Callback, Animatable {
    protected PullToRefreshView mRefreshLayout;

    public BaseRefreshView(PullToRefreshView mRefreshLayout) {
        this.mRefreshLayout = mRefreshLayout;
    }

    public Context getContext() {
        return mRefreshLayout == null ? null : mRefreshLayout.getContext();
    }

    /**
     * @param percent
     * @param invalidate
     */
    public abstract void setPercent(float percent, boolean invalidate);

    /**
     * @param offset
     */
    public abstract void offsetTopAndBottom(int offset);

    /**
     * @param viewWidth
     */
    protected abstract void initialDimens(int viewWidth);

    /**
     * 创建动画
     */
    protected abstract void setupAnimations();

    protected int getPixel(int dp) {
        return DisplayUtils.convertDpToPx(getContext(), dp);
    }

    /**
     * callBack回调:当drawable需要重回的时候调运
     * @param drawable
     */
    @Override
    public void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null)
            callback.invalidateDrawable(drawable);
    }

    @Override
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {
        Callback callback = getCallback();
        if (callback != null)
            callback.scheduleDrawable(drawable, runnable, l);
    }

    @Override
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null)
            callback.unscheduleDrawable(drawable, runnable);
    }

    //************必须实现,自定义Drawable核心为draw方法,下面四个方法都是围绕draw核心***********//
    @Override
    public int getOpacity() {
        //半透明
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }
}
