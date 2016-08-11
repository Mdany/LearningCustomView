package com.chenyu.monster.customviewtest.view.circleImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by chenyu on 16/8/11.
 */
public class DrawableCircleView extends Drawable {

    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    private int type;

    private Bitmap bitmap;
    private BitmapShader bitmapShader;
    private Paint mPaint;
    private RectF rectF;
    private Context context;
    private int mWidth;

    public DrawableCircleView(Context context, Bitmap bitmap, int type) {
        this.context = context;
        this.bitmap = bitmap;
        this.type = type;
        this.bitmapShader = new BitmapShader(this.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    public int getIntrinsicWidth() {
        return type == TYPE_ROUND ? bitmap.getWidth() : mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return type == TYPE_ROUND ? bitmap.getHeight() : mWidth;
    }

    @Override
    public void draw(Canvas canvas) {
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(rectF, dpToPx(100), dpToPx(100), mPaint);
        } else {
            canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
