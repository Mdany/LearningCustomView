package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by chenyu on 16/6/21.
 */
public class ChooseImageView extends ImageView {
    private Drawable mDrawable;
    private Bitmap mBitmap;

    public ChooseImageView(Context context) {
        super(context);
    }

    public ChooseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChooseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable = getDrawable();
        mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //匹配模型drawable
                matchModelDrawable();
                break;
            case MotionEvent.ACTION_UP:
                //还原基础drawable
                resetDrawable();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void matchModelDrawable() {
    }

    private void resetDrawable() {
    }

    private enum PositionType{
    }

    /**
     * 判断点击位置是否在区域内
     *
     * @param bitmap
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointInsideArea(Bitmap bitmap, int x, int y) {
        if (bitmap == null)
            throw new RuntimeException("isTouchPointInsideArea() bitmap is null");
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        return rect.contains(x, y);
    }

    /**
     * 如果点击位置在匹配位置,则判断点击位置是否透明,
     * 不透明则显示所在区域选中状态图
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointTransparent(int x, int y) {
        if (mBitmap == null)
            throw new RuntimeException("isTouchPointTransparent() bitmap is null");
        int pixel = mBitmap.getPixel(x, y);
        return pixel == Color.TRANSPARENT;
    }
}
