package com.chenyu.monster.customviewtest.view.pullToRefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

/**
 * Created by chenyu on 16/8/9.
 */
public class CreateBitmapFactory {
    private static BitmapFactory.Options mOptions;

    static {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    static Bitmap getBitmapFromImage(@DrawableRes int id, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), id, mOptions);
    }
}
