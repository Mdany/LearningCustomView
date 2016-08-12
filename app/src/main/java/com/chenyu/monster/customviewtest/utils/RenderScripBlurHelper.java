package com.chenyu.monster.customviewtest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurHelper {
    public static final float BITMAP_SCALE = 0.4f;
    public static final float MAX_BLUR_RADIUS = 20f;

    public static Bitmap blur(Context context, Bitmap paramBitmap, float radius) {
        long now = System.currentTimeMillis();
        Log.i("blur", "" + now);
        int width = Math.round(paramBitmap.getWidth() * BITMAP_SCALE);
        int height = Math.round(paramBitmap.getHeight() * BITMAP_SCALE);
        Log.i("blur", width + "-----" + height);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(paramBitmap, width, height, false);

        Bitmap outputBitmap = inputBitmap.copy(Bitmap.Config.ARGB_8888, true);

        if (Bitmap.Config.ARGB_8888 != outputBitmap.getConfig()) {
            //RenderScrip hate ARGB_565
            outputBitmap = outputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        Log.i("blur", "" + (System.currentTimeMillis() - now));
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation inAlloc = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation outAlloc = Allocation.createFromBitmap(rs, outputBitmap);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 设置blurScript对象的输入内存
        blur.setInput(inAlloc);
        // 设置渲染的模糊程度
        blur.setRadius(radius);
        // 将输出数据保存到输出内存中
        blur.forEach(outAlloc);
        // 将数据填充到Allocation中
        outAlloc.copyTo(outputBitmap);

        Log.i("blur", "" + (System.currentTimeMillis() - now));
        // 回收该内核  耗时很多
        rs.destroy();
        Log.i("blur", "" + (System.currentTimeMillis() - now));
        return outputBitmap;
    }

    public static Bitmap addColorToBitmap(Bitmap bitmap, int color) {
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        return bitmap;
    }
}
