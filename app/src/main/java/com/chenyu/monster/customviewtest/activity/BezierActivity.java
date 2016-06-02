package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.BezierView;

/**
 * Created by chenyu on 16/5/30.
 */
public class BezierActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new BezierView(this));
    }
}
