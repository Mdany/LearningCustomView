package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.pathMeasure.PathEffectView;
import com.chenyu.monster.customviewtest.view.pathMeasure.PathMeasureView;

/**
 * Created by chenyu on 16/8/18.
 */
public class PathMeasureActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new PathMeasureView(this));
        container.addView(new PathEffectView(this));
    }
}
