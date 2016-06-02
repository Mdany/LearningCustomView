package com.chenyu.monster.customviewtest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 16/5/30.
 */
public class ViewPagerBezierIndicator extends View {
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

    private List<PointF> pointFs;

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
        animators = new ArrayList<>();
        pointFs = new ArrayList<>();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mode) {
            case MODE_NORMAL:
                drawModeNormal(canvas);
                break;
            case MODE_BEND:
                break;
        }
    }

    private void drawModeNormal(Canvas canvas) {
        path.reset();

        path.moveTo(rightCircleX, mHeight / 2);
        path.lineTo(rightCircleX, mHeight / 2 - rightCircleRadius);
        path.quadTo(rightCircleX, mHeight / 2 - rightCircleRadius, leftCircleX, mHeight / 2 - leftCircleRadius);
        path.lineTo(leftCircleX, mHeight / 2 + leftCircleRadius);
        path.quadTo(leftCircleX, mHeight / 2 + leftCircleRadius, rightCircleX, mHeight / 2 + rightCircleRadius);
        path.close();
        canvas.drawPath(path,paint);
    }

    private void drawModeBend(){
        float middleOffset = (leftCircleX -rightCircleX);
    }
}



























