package com.chenyu.monster.customviewtest;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by chenyu on 16/3/16.
 */
public class DisplayUtils {
    public static int getScreenWidth(Context mContext) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context mContext) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int convertDpToPx(Context context, int dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return Math.round(dp * dm.density);
    }

    public static int convertPxToDp(Context context, int px) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return Math.round(px / dm.density);
    }
}
