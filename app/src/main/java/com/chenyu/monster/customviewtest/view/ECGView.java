package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by chenyu on 16/3/28.
 */
public class ECGView extends View {
    private Paint paint;
    private Path path;
    private int width, height;//screen's w & h
    private float x, y;//path start location
    private float initWidth;//screen init width
    private float initX;//location init x
    private float transX, moveX;//canvas move distance
    private boolean isCanvasMove;// is canvas can be moved?

    public ECGView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        transX = 0;
        isCanvasMove = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        x = 0;
        y = height / 2 + height / 4 + height / 10;

        initX = width / 2 + width / 4;
        moveX = width / 24;
        path.moveTo(x, y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        path.lineTo(x, y);
        canvas.translate(-transX, 0);
        calCoors();
        canvas.drawPath(path, paint);
        invalidate();
    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (isCanvasMove == true) {
            transX += 4;
        }

        if (x < initX) {
            x += 8;
        } else {
            if (x < initX + moveX) {
                x += 2;
                y -= 8;
            } else {
                if (x < initX + (moveX * 2)) {
                    x += 2;
                    y += 14;
                } else {
                    if (x < initX + (moveX * 3)) {
                        x += 2;
                        y -= 12;
                    } else {
                        if (x < initX + (moveX * 4)) {
                            x += 2;
                            y += 6;
                        } else {
                            if (x < width) {
                                x += 8;
                            } else {
                                isCanvasMove = true;
                                initX = initX + width;
                            }
                        }
                    }
                }
            }

        }
    }
}
