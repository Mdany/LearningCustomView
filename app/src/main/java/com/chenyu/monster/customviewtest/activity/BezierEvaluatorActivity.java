package com.chenyu.monster.customviewtest.activity;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.widget.TextView;

import com.chenyu.monster.customviewtest.view.BezierEvaluator;

/**
 * Created by chenyu on 16/5/30.
 */
public class BezierEvaluatorActivity extends BaseActivity {
    private TextView textView;

    @Override
    protected void viewDidLoad() {
        textView = new TextView(this);
        container.addView(textView);
        PointF p0 = new PointF(100, 100);
        PointF p1 = new PointF(800, 100);
        PointF p2 = new PointF(100, 800);
        PointF p3 = new PointF(800, 800);

        BezierEvaluator evaluator = new BezierEvaluator(p0, p1, p2, p3);

        ValueAnimator animator = ValueAnimator.ofObject(evaluator, p0, p3)
                .setDuration(4500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                textView.setX(pointF.x);
                textView.setY(pointF.y);
            }

        });

        animator.start();
    }


}
