package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.ECGView;

/**
 * Created by chenyu on 16/3/28.
 */
public class ECGViewActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new ECGView(this));
    }
}
