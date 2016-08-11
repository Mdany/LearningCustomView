package com.chenyu.monster.customviewtest.view.circleImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/8/11.
 */
public class BitmapShaderCircleView extends ImageView {
    public static final int TYPE_ROUND = 1;
    public static final int TYPE_CIRCLE = 0;
    private int mType;

    public static final int DEFAULT_ROUND = 10;
    private int mRadius;

    private BitmapShader mBitmapShader;
    private Matrix mMatrix;
    private Paint mPaint;

    public BitmapShaderCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XfermodeCircleView);
        mType = array.getInt(R.styleable.XfermodeCircleView_type, TYPE_CIRCLE);
        mRadius = array.getDimensionPixelSize(R.styleable.XfermodeCircleView_radius,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        DEFAULT_ROUND,
                        context.getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mType == TYPE_CIRCLE) {
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = width / 2;
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setupShader();
        if (mType == TYPE_CIRCLE)
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        else
            canvas.drawRoundRect(0, 0, getWidth(), getHeight(), mRadius, mRadius, mPaint);
    }

    private void setupShader() {
        Drawable drawable = getDrawable();
        if (drawable == null)
            return;

        Bitmap bitmap = drawableToBitmap(drawable);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float dWidth = drawable.getIntrinsicWidth();
        float dHeight = drawable.getIntrinsicHeight();

        float scale;
        if (mType == TYPE_ROUND) {
            scale = Math.max(getWidth() / dWidth, getHeight() / dHeight);
        } else {
//            scale = getWidth() / Math.min(dWidth, dHeight);
            scale = Math.max(getWidth() / dWidth, getWidth() / dHeight);
        }

        mMatrix.setScale(scale, scale);

        mBitmapShader.setLocalMatrix(mMatrix);

        mPaint.setShader(mBitmapShader);
    }

    /**
     * drawable转换成bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }
}
