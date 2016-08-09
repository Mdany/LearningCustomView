package com.chenyu.monster.customviewtest.view.pullToRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/8/9.
 * thanks for lumeng(jiahehz@gmail.com)
 * https://github.com/lubeast/PullToRefresh
 * 一个下拉刷新源码的学习
 */
public class PullToRefreshView extends ViewGroup {
    /**
     * 两种style默认直
     */
    public static final int TYPE_ROCKET= 0;
    public static final int TYPE_SUN = 1;
    public static final float DECELEARE_INTERPOLATION_FACTOR = 2f;

    private int mTotalDragDistance;
    private int mTouchSlop;

    private View mTarget;
    private ImageView mRefreshView;
    private Interpolator mDecelerateInterpolator;

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PullToRefreshView);
        final int type = array.getInteger(R.styleable.PullToRefreshView_refreshStyle,TYPE_ROCKET);
        array.recycle();

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDecelerateInterpolator = new DecelerateInterpolator(DECELEARE_INTERPOLATION_FACTOR);

        mRefreshView = new ImageView(context);

        addView(mRefreshView);
        //自定义ViewGroup要设置
        setWillNotDraw(false);
        //低版本上兼容上面的方法的效果
        ViewCompat.setChildrenDrawingOrderEnabled(this,true);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
    }
}
