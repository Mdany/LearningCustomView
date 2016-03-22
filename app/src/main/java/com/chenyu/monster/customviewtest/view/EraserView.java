package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chenyu.monster.customviewtest.DisplayUtils;
import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/3/18.
 */
public class EraserView extends View {
    private static final int MIN_MOVE_DIS = 5;//最小移动距离，小于该值不绘制

    private Bitmap fgBitmap, bgBitmap;//橡皮擦前背景和底部背景
    private Canvas mCanvas;//路径画布
    private Paint mPaint;//橡皮擦画笔
    private Path mPath;//橡皮擦路径

    private int screenW, screenH;

    private float preX, preY;//记录上一个触摸事件的位置坐标

    public EraserView(Context context) {
        super(context);
        cal(context);
        init(context);
    }

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cal(context);
        init(context);
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cal(context);
        init(context);
    }

    public EraserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        cal(context);
        init(context);
    }

    private void cal(Context context) {
        screenW = DisplayUtils.getScreenWidth(context);
        screenH = DisplayUtils.getScreenHeight(context);
    }

    private void init(Context context) {
        //实例化路径对象
        mPath = new Path();
        //实例化画笔开启抗锯齿和抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //设置画笔透明底为0，与前景色混合“抠出”路径
        mPaint.setARGB(128, 255, 0, 0);
        //设置混合模式为DST_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //设置画笔风格为描边
        mPaint.setStyle(Paint.Style.STROKE);
        //设置路径结合处的样式
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置笔触类型
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置描边宽度
        mPaint.setStrokeWidth(50);
        //生成前景图bitmap
        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        //将前景图注入canvas
        mCanvas = new Canvas(fgBitmap);
        //绘制画布背景为中性灰
        mCanvas.drawColor(0xFF808080);
        //获取背景底图bitmap
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        //缩放背景图片Bitmap至屏幕大小
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, screenW, screenH, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(fgBitmap, 0, 0, null);


        /*
         * fg和bg都在canvas上，mCanvas则是fg与path，两者融合透明效果
         * 手指移动的路径通过mCanvas绘制到fgBitmap上面，mCanvas为中性灰背景色，因为DST_IN效果，只显示中性灰，
         * 而path的透明，计算混合图像也为透明
         */
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指触摸屏幕重置路径
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE://手指移动时链接路径
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    mPath.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;
        }
        invalidate();
        return true;
    }
}
