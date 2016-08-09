package com.chenyu.monster.customviewtest.view.tencentDrawLayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by chenyu on 16/8/3.
 */
public class CustomDrawerLayout extends ViewGroup {
    private ViewDragHelper helper;

    private View mTopView;
    private View mBottomView;

    /**
     * 滑动百分比
     */
    private float mCurMovePercent = 0;

    public CustomDrawerLayout(Context context) {
        super(context);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, 1, new ViewDragHelperCallBack());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //只有两个子成员才有效
        if (getChildCount() == 2) {
            //child view是倒序排列
            mBottomView = getChildAt(0);
            mTopView = getChildAt(1);

            MarginLayoutParams params = (MarginLayoutParams) mBottomView.getLayoutParams();
            //限制大小为700以下
            int width = 0;
            width = (params.width < 0 || params.width > 700) ? 600 : params.width;
            int btmWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            measureChild(mBottomView, btmWidthMeasureSpec, heightMeasureSpec);
            measureChild(mTopView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //只有layout changed了并且有两个子成员才有效
        if (changed && getChildCount() == 2) {
            MarginLayoutParams params = (MarginLayoutParams) mBottomView.getLayoutParams();
            mBottomView.layout(params.leftMargin, params.topMargin,
                    mBottomView.getMeasuredWidth() + params.leftMargin,
                    mBottomView.getMeasuredHeight() + params.topMargin);

            params = (MarginLayoutParams) mTopView.getLayoutParams();
            mTopView.layout(params.leftMargin, params.topMargin,
                    mTopView.getMeasuredWidth() + params.leftMargin,
                    mTopView.getMeasuredHeight() + params.topMargin);
        }
    }

    //通过smoothSlideViewTo打开侧栏，因为其他两个方法只能在callback的release中调运
    public void openLeftView() {
        //指定某个View自动滚动到指定的位置，初速度为0，可在任何地方调用
        //如果这个方法返回true，那么在接下来动画移动的每一帧中都会回调continueSettling(boolean)方法，直到结束
        smoothSlideViewTo(mBottomView.getMeasuredWidth());
    }

    //通过smoothSlideViewTo关闭侧栏，因为其他两个方法只能在callback的release中调运
    public void closeLeftView() {
        //指定某个View自动滚动到指定的位置，初速度为0，可在任何地方调用
        //如果这个方法返回true，那么在接下来动画移动的每一帧中都会回调continueSettling(boolean)方法，直到结束
        smoothSlideViewTo(0);
    }

    private void smoothSlideViewTo(int finalLeft) {
        if (this.helper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            this.helper.abort();
        }
        //指定某个View自动滚动到指定的位置，初速度为0，可在任何地方调用
        //如果这个方法返回true，那么在接下来动画移动的每一帧中都会回调continueSettling(boolean)方法，直到结束
        this.helper.smoothSlideViewTo(mTopView, finalLeft, mTopView.getTop());
        invalidate();
    }

    //判断当前左边侧栏是否打开状态，通过mLeftShowSize进行判断
    public boolean isLeftIsOpened() {
        return mTopView.getLeft() == mBottomView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean helper = this.helper.shouldInterceptTouchEvent(ev);
        boolean result = false;
        float mInitXpos = 0;
        float mInitYpos = 0;

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                mInitXpos = ev.getX();
                mInitYpos = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX()-mInitXpos) - Math.abs(ev.getY()-mInitYpos) > ViewConfiguration.getTouchSlop()) {
                    result = true;
                    //ACTION_MOVE时已经过了tryCaptureView，故想挪动则使用captureChildView即可
                    this.helper.captureChildView(mTopView, ev.getPointerId(0));
                }
                break;
        }
        return helper || result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.helper.processTouchEvent(event);
        return true;
    }

    /**
     * 自定义ViewDragHelper回调Callback
     */
    private class ViewDragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //类似侧滑栏打开时也可以通过触摸左边区域挪动mTopView位置,类似于onEdgeDragStarted中的调运
            helper.captureChildView(mTopView, pointerId);
            //当前拖拽的子view如果为mTopView则返回true
            return child == mTopView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //默认不允许水平移动super.clampViewPositionHorizontal(child, left, dx);
            //(left)水平实时变化,限制最大挪移位置处理,左移为0 右移最大为mBottomView.getMeasuredWidth()
            return Math.min(Math.max(0, left), mBottomView.getMeasuredWidth());
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            //返回给定子View在相应方向上可以被拖动的最远距离，默认为0，一般是可被挪动View时指定为指定View的大小等
            //super.getViewHorizontalDragRange(child);
            return child == mTopView ? mTopView.getMeasuredWidth() : 0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //changedView为位置变化的View，left/top变化时新的x左/y顶坐标，dx/dy为从旧到新的偏移量
            //super.onViewPositionChanged(changedView, left, top, dx, dy);

            //计算出mTopView变化时占总变化宽度的实时百分比
            if (changedView == mTopView) {
                mCurMovePercent = (mBottomView.getMeasuredWidth() - left) / ((float) mBottomView.getMeasuredWidth());
            }

            //mCurMovePercent(1,0) -> mTopView(1,0.8),一元二次方程m = mx+c
            float topViewScale = 0.2f * mCurMovePercent + 0.8f;
            ViewHelper.setScaleX(mTopView, topViewScale);   //mTopView.setScaleX();
            ViewHelper.setScaleY(mTopView, topViewScale);   //mTopView.setScaleY();
            //btmPrecent(0.8, 1)->topViewScale(1, 0.8)
            float btmPrecent = 1.8f-topViewScale;
            ViewHelper.setScaleY(mBottomView, btmPrecent);   //mBottomView.setScaleX();
            ViewHelper.setScaleX(mBottomView, btmPrecent);   //mBottomView.setScaleY();
            ViewHelper.setAlpha(mBottomView, btmPrecent);    //mBottomView.setAlpha();

            float btmTransX = -mBottomView.getMeasuredWidth() * mCurMovePercent;
            ViewHelper.setTranslationX(mBottomView, btmTransX);    //mBottomView.setTranslationX();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //当子View被松手或者ACTION_CANCEL时时回调，xvel/yvel为离开屏幕时各方向每秒运动的速率，为px
            if (releasedChild == mTopView) {
                int finalLeft = (xvel > 0 && mCurMovePercent < 0.5) ? mBottomView.getMeasuredWidth() : 0;
                //以松手前的滑动速度为初值，让捕获到的子View自动滚动到指定位置，只能在Callback的onViewReleased()中使用
                //如果这个方法返回true，那么在接下来动画移动的每一帧中都会回调continueSettling(boolean)方法，直到结束
                helper.settleCapturedViewAt(finalLeft, mTopView.getTop());
                invalidate();
            }
        }
    }

    /**
     * 在整个settle状态中,这个方法会返回true，deferCallbacks决定滑动是否Runnable推迟，一般推迟
     * 在调用settleCapturedViewAt()、flingCapturedView()和smoothSlideViewTo()时，
     * 需要实现mParentView的computeScroll()方法，如下：
     *
     * @Override
     * public void computeScroll() {
     *     if (mDragHelper.continueSettling(true)) {
     *         ViewCompat.postInvalidateOnAnimation(this);
     *     }
     * }
     */
    @Override
    public void computeScroll() {
        if (helper!= null&&helper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
