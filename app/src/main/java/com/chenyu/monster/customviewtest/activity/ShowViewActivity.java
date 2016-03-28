package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.ColorMatrixView;

/**
 * Created by chenyu on 16/3/16.
 */
public class ShowViewActivity extends BaseActivity {
    private ColorMatrixView customView;

    @Override
    protected void viewDidLoad() {
        customView = new ColorMatrixView(this);
        addView(customView);
    }
}
