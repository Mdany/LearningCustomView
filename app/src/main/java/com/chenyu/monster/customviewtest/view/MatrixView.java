package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chenyu.monster.customviewtest.DisplayUtils;

/**
 * Created by chenyu on 16/3/28.
 */
public class MatrixView extends View {
    private float canvasRotateMaxDegree;
    private Paint paint;
    private int centerX;
    private int centerY;

    private float touchX, touchY;
    private Camera camera;
    private Matrix matrix;

    //canvas旋转 变量
    private float canvasRotateX = 0;
    private float canvasRotateY = 0;

    private int mAlpha = 200;
    private Path mPath;
    private double alpha;

    public MatrixView(Context context) {
        super(context);
        init(context);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        centerX = DisplayUtils.getScreenWidth(context) / 2;
        centerY = DisplayUtils.getScreenHeight(context) / 2;
        canvasRotateMaxDegree = 20;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();

        camera = new Camera();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#227BAE"));
        //小圆点指示器跟随画布旋转
        canvas.rotate((float) alpha, centerX, centerY);
        alpha = Math.atan((touchX - centerX) / (centerY - touchY));
        alpha = Math.toDegrees(alpha);
        if (touchY > centerY) {
            alpha = alpha + 180;
        }

        //旋转画布
        rotateCanvas(canvas);

        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);

        canvas.drawText("N", centerX, 150, paint);

        drawCircle(canvas);
        drawIndicateCircle(canvas);
        drawPath(canvas);
    }

    /**
     * 旋转画布刻线
     *
     * @param canvas
     */
    private void rotateCanvas(Canvas canvas) {
        matrix.reset();
        camera.save();
        camera.rotateX(canvasRotateX);
        camera.rotateY(canvasRotateY);
        camera.getMatrix(matrix);
        camera.restore();

        //改变矩阵作用点，不从0，0开始，而是为中心点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);

        //matrix作用于整个canvas
        canvas.concat(matrix);
    }

    /**
     * 旋转画布画线
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 120; i++) {
            paint.setAlpha(255 - (mAlpha * i / 120));
            canvas.rotate(3, centerX, centerY);
            canvas.drawLine(centerX, 250, centerX, 270, paint);
        }
        canvas.restore();
    }

    /**
     * 画圆指示器
     *
     * @param canvas
     */
    private void drawIndicateCircle(Canvas canvas) {
        paint.setAlpha(255);
        canvas.drawCircle(centerX, 290, 10, paint);
    }

    /**
     * 画个指针
     */
    private void drawPath(Canvas canvas) {
        mPath.moveTo(centerX, 293);
        mPath.lineTo(centerX - 30, centerY);
        mPath.lineTo(centerX, 2 * centerY - 293);
        mPath.lineTo(centerX + 30, centerY);
        mPath.lineTo(centerX, 293);
        mPath.close();

        canvas.drawPath(mPath, paint);
        paint.setColor(Color.parseColor("#55227BAE"));
        canvas.drawCircle(centerX, centerY, 20, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        touchX = x;
        touchY = y;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                canvasRotateX = 0;
                canvasRotateY = 0;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 旋转限度限制旋转最大值
     * @param x
     * @param y
     */
    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - centerX;
        float dy = y - centerY;

        float percentX = dx / centerX;
        float percentY = dy / centerY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        canvasRotateY = canvasRotateMaxDegree * percentX;
        canvasRotateX = -(canvasRotateMaxDegree * percentY);
    }
}
