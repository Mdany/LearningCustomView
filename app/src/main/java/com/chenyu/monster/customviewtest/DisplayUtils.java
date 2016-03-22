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
}
