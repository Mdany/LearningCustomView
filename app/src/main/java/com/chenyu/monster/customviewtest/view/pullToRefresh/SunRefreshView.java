package com.chenyu.monster.customviewtest.view.pullToRefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/8/9.
 */
public class SunRefreshView extends BaseRefreshView {
    /**
     * 宽高比
     */
    public static final float HEIGHT_RATIO = 1.0f;

    /**
     * 展示的view的宽度
     */
    private int mScreenWidth;
    /**
     * 展示view的高度
     */
    private int mScreenHeight;
    /**
     * distance between bottom of landscape and top of landscape
     */
    private int mTop;
    /**
     * max distance between bottom of landscape and top of landscape
     */
    private int totalDistance;

    private float mBuildingOffset;
    private float mSunRotateAngle;
    private float mPercent;

    private int mSunWidth;
    private int mSunHeight;

    private boolean isRefreshing;

    private Context context;
    private Paint mBackgroundPaint;

    private Bitmap mSun;
    private Bitmap mBuilding;

    private Animation mSunAnimation;

    public SunRefreshView(final PullToRefreshView mRefreshLayout) {
        super(mRefreshLayout);
        context = getContext();
        this.mRefreshLayout = mRefreshLayout;
        setupAnimations();
        setupPaint();

        //待view layout完成后获取其对应的宽高参数
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                initialDimens(mRefreshLayout.getWidth());
            }
        });
    }

    /**
     * 设置背景画笔
     */
    private void setupPaint() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.rgb(251, 66, 49));
        mBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
        setPercent(percent);
    }

    private void setPercent(float percent) {
        this.mPercent = percent;
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        //回调callback.invalidateDrawable(drawable)
        invalidateSelf();
    }

    @Override
    protected void initialDimens(int viewWidth) {
        if (viewWidth < 0 || viewWidth == mScreenWidth) return;
        createBitmaps();

        mScreenWidth = viewWidth;
        mScreenHeight = (int) (HEIGHT_RATIO * mScreenWidth);
        mTop = -mRefreshLayout.getTop();
        totalDistance = -mTop;

        mBuildingOffset = -mTop - mBuilding.getHeight();
        mSunWidth = mSun.getWidth();
        mSunHeight = mSun.getHeight();
    }

    private void createBitmaps() {
        mSun = CreateBitmapFactory.getBitmapFromImage(R.drawable.sun, context);
        mBuilding = CreateBitmapFactory.getBitmapFromImage(R.drawable.home_title_building_hz, context);
    }

    @Override
    protected void setupAnimations() {
        AnimationFactory animationFactory = new AnimationFactory();
        mSunAnimation = animationFactory.getSunRotate(new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                mSunRotateAngle = 720 * setVariable(interpolatedTime);
            }
        });

        mSunAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                resetOrigins();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private float setVariable(float value) {
        invalidateSelf();
        return value;
    }
    //----------------------------------//

    @Override
    public void draw(Canvas canvas) {
        if (mScreenWidth<=0)return;

        int count = canvas.save();

        canvas.translate(0,mTop);


        canvas.restoreToCount(count);
    }


    //----------------------------------//

    @Override
    public void start() {
        isRefreshing = true;
        mSunAnimation.reset();
        mRefreshLayout.startAnimation(mSunAnimation);
    }

    @Override
    public void stop() {
        mRefreshLayout.clearAnimation();
        isRefreshing = false;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, mScreenHeight + top);
    }

    /**
     * 动画结束后,恢复初始状态
     */
    private void resetOrigins() {
        setPercent(0.0f);
        mSunRotateAngle = 0.0f;
    }
}
