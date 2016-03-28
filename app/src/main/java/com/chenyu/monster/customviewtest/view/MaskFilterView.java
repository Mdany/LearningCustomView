package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.chenyu.monster.customviewtest.DisplayUtils;
import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/3/28.
 */
public class MaskFilterView extends View {
    private static final int RECT_SIZE = 800;
    private Paint shadowPaint;
    private Context context;
    private Bitmap srcBitmap, shadowbitmap;

    private int x, y;//左上角起点坐标

    public MaskFilterView(Context context) {
        super(context);
        initPaint();
        initRes(context);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initRes(context);
    }

    public MaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initRes(context);
    }

    public MaskFilterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        initPaint();
        initRes(context);
    }

    private void initPaint() {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        shadowPaint.setColor(Color.DKGRAY);

        shadowPaint.setMaskFilter(new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL));//NORMAL\INNER\OUTER\SOLID
    }

    private void initRes(Context context) {
        this.context = context;
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        shadowbitmap = srcBitmap.extractAlpha();
        int width = DisplayUtils.getScreenWidth(context);
        int height = DisplayUtils.getScreenHeight(context);
        x = width / 2 - srcBitmap.getWidth() / 2;
        y = height / 2 - srcBitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(shadowbitmap, x, y, shadowPaint);
        canvas.drawBitmap(srcBitmap, x, y, shadowPaint);
    }
}
