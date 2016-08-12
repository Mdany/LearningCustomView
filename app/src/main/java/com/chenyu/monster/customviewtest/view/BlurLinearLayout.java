package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chenyu.monster.customviewtest.utils.RenderScripBlurHelper;

/**
 * Created by chenyu on 16/8/12.
 */
public class BlurLinearLayout extends LinearLayout {
    private Bitmap bitmap;

    public BlurLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setBackground(Drawable background) {
        bitmap = RenderScripBlurHelper.blur(getContext(), drawableToBitmap(background), RenderScripBlurHelper.MAX_BLUR_RADIUS);
        super.setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }
}
