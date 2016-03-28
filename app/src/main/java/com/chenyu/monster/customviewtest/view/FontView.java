package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenyu on 16/3/22.
 */
public class FontView extends View {
    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint mPaint;
    private Paint.FontMetrics fontMetrics;

    public FontView(Context context) {
        super(context);
        init();
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);
        fontMetrics = mPaint.getFontMetrics();

        Log.d("wcy", "ascent：" + fontMetrics.ascent);
        Log.d("wcy", "top：" + fontMetrics.top);
        Log.d("wcy", "leading：" + fontMetrics.leading);
        Log.d("wcy", "descent：" + fontMetrics.descent);
        Log.d("wcy", "bottom：" + fontMetrics.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(TEXT, 0, Math.abs(fontMetrics.top), mPaint);
    }
}
