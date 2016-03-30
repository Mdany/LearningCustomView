package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.chenyu.monster.customviewtest.DisplayUtils;
import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/3/30.
 */
public class ShaderView extends View {
    private static final int RECT_SIZE = 50;
    private Paint paint;
    private int left, top, right, bottom;

    public ShaderView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        int width = DisplayUtils.getScreenWidth(context);
        int height = DisplayUtils.getScreenHeight(context);

        left = width - RECT_SIZE / 2;
        top = height - RECT_SIZE / 2;
        right = width + RECT_SIZE / 2;
        bottom = height + RECT_SIZE / 2;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a1);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK);
        //paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
