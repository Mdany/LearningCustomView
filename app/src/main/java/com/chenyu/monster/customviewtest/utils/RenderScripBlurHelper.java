package com.chenyu.monster.customviewtest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurHelper {

    public static Bitmap blur(Context context, Bitmap paramBitmap, int radius, boolean canReuseBitmap) {
        Bitmap bitmap;
        if (canReuseBitmap) {
            bitmap = paramBitmap;
        } else {
            bitmap = paramBitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        if (Bitmap.Config.ARGB_8888 != bitmap.getConfig()) {
            //RenderScrip hate ARGB_565
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        RenderScript rs = RenderScript.create(context);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bitmap);
        rs.destroy();
        return bitmap;
    }

    public static Bitmap addColorToBitmap(Bitmap bitmap, int color) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        return bitmap;
    }
}
