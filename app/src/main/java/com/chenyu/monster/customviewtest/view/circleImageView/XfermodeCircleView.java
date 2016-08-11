package com.chenyu.monster.customviewtest.view.circleImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.R;

import java.lang.ref.WeakReference;

/**
 * Created by chenyu on 16/8/9.
 * thanks for hongyang
 * http://blog.csdn.net/lmj623565791/article/details/42094215
 */
public class XfermodeCircleView extends ImageView {
    /**
     * 默认显示的类型:圆角 or 圆形
     */
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    private int mType;
    /**
     * 默认圆角大小
     */
    public static final int DEFAULT_RADIUS = 10;
    /**
     * 圆角实际大小
     */
    private int mRadius;

    private Paint mPaint;
    private WeakReference<Bitmap> mWeakBitmap;
    private Drawable srcDrawable;
    private Xfermode mXfermode;
    private Bitmap mMaskBitmap;

    public XfermodeCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XfermodeCircleView);

        mType = array.getInt(R.styleable.XfermodeCircleView_type, TYPE_CIRCLE);
        mRadius = array.getDimensionPixelSize(
                R.styleable.XfermodeCircleView_radius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS,
                        context.getResources().getDisplayMetrics()));
        srcDrawable = getDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mType == TYPE_CIRCLE) {
            int minSpec = Math.min(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(minSpec, minSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //缓存中取出bitmap
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (bitmap == null || bitmap.isRecycled()) {
            if (srcDrawable != null) {
                //获取drawable宽高
                int dWidth = srcDrawable.getIntrinsicWidth();
                int dHeight = srcDrawable.getIntrinsicHeight();
                //创建个bitmap
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                float scale;
                //创建画布
                Canvas drawableCanvas = new Canvas(bitmap);
                if (mType == TYPE_ROUND) {
                    scale = Math.max(getWidth() * 1.0f / dWidth, getHeight() * 1.0f / dHeight);
                } else {
                    scale = getWidth() * 1.0f / Math.min(dWidth, dHeight);
                }
                //重新调整drawable绘制区域大小
                srcDrawable.setBounds(0, 0, (int) (scale * dWidth), (int) (scale * dHeight));
                //重绘的drawable
                srcDrawable.draw(drawableCanvas);
                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getDstBitmap();//获取要绘制的形状
                }
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                drawableCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                //缓存bitmap
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
        }

        if (bitmap != null) {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }
    }

    /**
     * 绘制形状bitmap
     *
     * @return
     */
    private Bitmap getDstBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        if (TYPE_ROUND == mType) {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mRadius, mRadius, paint);
        } else {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        }
        return bitmap;
    }
}
