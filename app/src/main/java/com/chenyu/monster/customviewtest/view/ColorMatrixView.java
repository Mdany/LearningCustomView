package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.chenyu.monster.customviewtest.DisplayUtils;

/**
 * Created by chenyu on 16/3/16.
 */
public class ColorMatrixView extends View {
    private Context mContext;
    private Paint mPaint;

    public ColorMatrixView(Context context) {
        super(context);
        init(context);
    }

    public ColorMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ColorMatrixView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
        mPaint.setStrokeWidth(10);
        ColorMatrix matrix = new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(DisplayUtils.getScreenWidth(mContext) / 2
                , DisplayUtils.getScreenHeight(mContext) / 2, 200, mPaint);
    }
}
