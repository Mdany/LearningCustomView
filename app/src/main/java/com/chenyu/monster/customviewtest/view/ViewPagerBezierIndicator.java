package com.chenyu.monster.customviewtest.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 16/5/30.
 */
public class ViewPagerBezierIndicator extends View {
    private final String TAG = "BezierIndicator";
    private final long DURATION = 5000L;
    public static final int MODE_NORMAL = 1;
    public static final int MODE_BEND = 2;
    private Context context;

    private List<Integer> colors;//indicator 多个颜色
    private ArrayList<ValueAnimator> animators;//多动画

    private int mWidth;//宽度
    private int mHeight;//高度

    private float leftCircleRadius;//左圆半径
    private float leftCircleX;//左圆圆心x

    private float rightCircleRadius;//右圆半径
    private float rightCircleX;//右圆圆心x

    private List<PointF> pointFs;//点组
    private int mPageCount;//页数

    private float mMaxCircleRadius;//最大半径
    private float mMinCircleRadius;//最小半径

    private Paint paint;
    private Path path;

    private int mode = MODE_NORMAL;

    public ViewPagerBezierIndicator(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ViewPagerBezierIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ViewPagerBezierIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public ViewPagerBezierIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        Log.i(TAG, "init");
        animators = new ArrayList<>();
        pointFs = new ArrayList<>();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        canvas.drawCircle(leftCircleX, mHeight / 2, leftCircleRadius, paint);
        canvas.drawCircle(rightCircleX, mHeight / 2, rightCircleRadius, paint);
        switch (mode) {
            case MODE_NORMAL:
                drawModeNormal(canvas);
                break;
            case MODE_BEND:
                drawModeBend(canvas);
                break;
        }
    }

    /**
     * 二阶Bezier
     *
     * @param canvas
     */
    private void drawModeNormal(Canvas canvas) {
        Log.i(TAG, "drawModeNormal");
        path.reset();

        path.moveTo(rightCircleX, mHeight / 2);
        path.lineTo(rightCircleX, mHeight / 2 - rightCircleRadius);
        path.quadTo(rightCircleX, mHeight / 2 - rightCircleRadius, leftCircleX, mHeight / 2 - leftCircleRadius);
        path.lineTo(leftCircleX, mHeight / 2 + leftCircleRadius);
        path.quadTo(leftCircleX, mHeight / 2 + leftCircleRadius, rightCircleX, mHeight / 2 + rightCircleRadius);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 三阶Bezier
     *
     * @param canvas
     */
    private void drawModeBend(Canvas canvas) {
        Log.i(TAG, "drawModeBend");
        float middleOffset = (leftCircleX - rightCircleX) / (pointFs.get(1).x - pointFs.get(0).x) * (mHeight / 10);
        path.reset();
        path.moveTo(rightCircleX, mHeight / 2);
        path.lineTo(rightCircleX, mHeight / 2 - rightCircleRadius);
        path.cubicTo(rightCircleX, mHeight / 2 - rightCircleRadius,
                rightCircleX + (leftCircleX - rightCircleX) / 2f, mHeight / 2 + middleOffset,
                leftCircleX, mHeight / 2 - leftCircleRadius);
        path.lineTo(leftCircleX, mHeight / 2 + leftCircleRadius);

        path.cubicTo(leftCircleX, mHeight / 2 + leftCircleRadius,
                rightCircleX + (leftCircleX - rightCircleX) / 2.0f, mHeight / 2 - middleOffset,
                rightCircleX, mHeight / 2 + rightCircleRadius);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 获取view宽高,根据高度设置半径范围
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");
        mWidth = w;
        mHeight = h;
        mMaxCircleRadius = 0.45f * mHeight;
        mMinCircleRadius = 0.15f * mHeight;
        resetPoints();
    }

    /**
     * 根据宽度重置点组
     */
    private void resetPoints() {
        pointFs.clear();
        //求出所有点的x值,以下是一条直线
        for (int i = 0; i < mPageCount; i++) {
            int x = mWidth / (mPageCount + 1) * (i + 1);//只要前四段,第一段和第二段形成两边的空白区
            pointFs.add(new PointF(x, 0));
//            Log.i("wcy", x + "---" + i + "======" + mWidth);
        }

        if (!pointFs.isEmpty()) {
            //第一个点为起始值,左圆大,右圆小
            leftCircleX = pointFs.get(0).x;
            leftCircleRadius = mMaxCircleRadius;

            rightCircleX = pointFs.get(0).x;
            rightCircleRadius = mMinCircleRadius;

            postInvalidate();
        }
    }

    /**
     * 根据viewpager OnPageChangeListener中onPageScrolled返回的当前的position及offset滚动动画
     * 重复调用,重复创建动画给point并根据point坐标绘制view,设置duration保证view动画不会瞬间绘制完成
     *
     * @param position
     * @param offset
     */
    public void setPositionAndOffset(int position, float offset) {
        createAnimator(position);
        seekAnimator(offset);
    }

    /**
     * 属性动画,点动起来
     * 在增值器设置factor改变其对结果的影响
     *
     * @param position
     */
    private void createAnimator(int position) {
        Log.i(TAG, "createAnimator");
        if (pointFs.isEmpty()) {
            return;
        }
        animators.clear();
        int i = Math.min(mPageCount - 1, position + 1);//保证取值0~mPageCount-1
        //前后两个点
        float leftX = pointFs.get(position).x;
        float rightX = pointFs.get(i).x;
//        Log.i("wcy", position + "---" + i + "=======" + leftX + "-----" + rightX);
//        Log.i("wcy", leftCircleX + "-----" + rightCircleX);
//        Log.i("wcy", leftCircleRadius + "-----" + rightCircleRadius);

        //横坐标位置偏移
        ObjectAnimator leftPointAnimator = ObjectAnimator.ofFloat(this, "rightCircleX", leftX, rightX);
        leftPointAnimator.setDuration(DURATION);
        leftPointAnimator.setInterpolator(new DecelerateInterpolator());
        animators.add(leftPointAnimator);

        ObjectAnimator rightPointAnimator = ObjectAnimator.ofFloat(this, "leftCircleX", leftX, rightX);
        rightPointAnimator.setDuration(DURATION);
        rightPointAnimator.setInterpolator(new AccelerateInterpolator(1.5f));
        animators.add(rightPointAnimator);

        //圆半径长短增长或者缩短
        ObjectAnimator leftCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "rightCircleRadius", mMinCircleRadius, mMaxCircleRadius);
        leftCircleRadiusAnimator.setDuration(DURATION);
        leftCircleRadiusAnimator.setInterpolator(new AccelerateInterpolator(1.5f));
        animators.add(leftCircleRadiusAnimator);

        ObjectAnimator rightCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "leftCircleRadius", mMaxCircleRadius, mMinCircleRadius);
        rightCircleRadiusAnimator.setDuration(DURATION);
        rightCircleRadiusAnimator.setInterpolator(new DecelerateInterpolator(0.8f));
        animators.add(rightCircleRadiusAnimator);

        //颜色渐变
        int colors1 = colors.get(position);
        int colors2 = colors.get(i);
        ValueAnimator paintColorAnimator = ObjectAnimator.ofInt(colors1, colors2);
        paintColorAnimator.setDuration(DURATION);
        paintColorAnimator.setEvaluator(new ArgbEvaluator());
        paintColorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        paintColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                paint.setColor((Integer) animation.getAnimatedValue());
            }
        });
        animators.add(paintColorAnimator);
    }

    /**
     * 根据offset设置动画播放哪段时间的状态setCurrentPlayTime
     * viewPager 的 offset,正向(x正方向)为0~1
     * 反向(x轴反方向)为1~0
     *
     * @param offset
     */
    private void seekAnimator(float offset) {
        Log.i("wcy",offset+"");
        for (ValueAnimator animator : animators) {
            animator.setCurrentPlayTime((long) (offset * DURATION));
        }
        postInvalidate();
    }

    /**
     * 设置页数
     *
     * @param count
     */
    public void setPageCount(int count) {
        mPageCount = count;
    }

    /**
     * s设置模式
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 取第一个颜色为画笔颜色
     *
     * @param colors
     */
    public void setColors(List<Integer> colors) {
        this.colors = colors;
        if (colors != null && !colors.isEmpty()) {
            paint.setColor(colors.get(0));
        }
    }

    //---------------------以下get set方法为ValueAnimator使用
    public float getLeftCircleX() {
        return leftCircleX;
    }

    public void setLeftCircleX(float leftCircleX) {
        this.leftCircleX = leftCircleX;
    }

    public float getLeftCircleRadius() {
        return leftCircleRadius;
    }

    public void setLeftCircleRadius(float leftCircleRadius) {
        this.leftCircleRadius = leftCircleRadius;
    }

    public float getRightCircleRadius() {
        return rightCircleRadius;
    }

    public void setRightCircleRadius(float rightCircleRadius) {
        this.rightCircleRadius = rightCircleRadius;
    }

    public float getRightCircleX() {
        return rightCircleX;
    }

    public void setRightCircleX(float rightCircleX) {
        this.rightCircleX = rightCircleX;
    }
}



























