package com.chenyu.monster.customviewtest.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by chenyu on 16/5/30.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF[] points;

    public BezierEvaluator(PointF... points) {
        this.points = points;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float t = fraction;
        float one_t = 1.0f - t;

        PointF P0 = points[0];
        PointF P1 = points[1];
        PointF P2 = points[2];
        PointF P3 = points[3];

        float x = (float) (P0.x * Math.pow(one_t, 3) + 3 * P1.x * t * Math.pow(one_t, 2) + 3 * P2.x * Math.pow(t, 2) * one_t + P3.x * Math.pow(t, 3));
        float y = (float) (P0.y * Math.pow(one_t, 3) + 3 * P1.y * t * Math.pow(one_t, 2) + 3 * P2.y * Math.pow(t, 2) * one_t + P3.y * Math.pow(t, 3));

        PointF pointF = new PointF(x, y);

        return pointF;
    }

}
