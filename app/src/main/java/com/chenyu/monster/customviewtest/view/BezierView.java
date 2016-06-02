package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/5/30.
 */
public class BezierView extends View {
    private final String TAG = "BezierView";
    private Context context;
    private Paint paint;
    private Path path;

    public BezierView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        Log.i(TAG,"init");
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG,"onDraw");
        super.onDraw(canvas);
        path.moveTo(100, 100);
        path.cubicTo(800, 100, 100, 800, 800, 800);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG,"onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i(TAG,"onAttachToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i(TAG,"onDetachFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        Log.i(TAG,"onFinishInflate");
        super.onFinishInflate();
    }
}


























