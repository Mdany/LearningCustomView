package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.utils.RenderScripBlurHelper;

/**
 * Created by chenyu on 16/8/12.
 */
public class BlurImageView extends ImageView {

    public BlurImageView(Context context) {
        super(context);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        Bitmap bitmap = RenderScripBlurHelper.blur(getContext(), bm, RenderScripBlurHelper.MAX_BLUR_RADIUS);
        super.setImageBitmap(bitmap);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        Bitmap bitmap = RenderScripBlurHelper.blur(getContext(), drawableToBitmap(drawable), RenderScripBlurHelper.MAX_BLUR_RADIUS);
        super.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
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
