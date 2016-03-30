package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.MatrixView;

/**
 * Created by chenyu on 16/3/28.
 */
public class MatrixViewActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new MatrixView(this));
    }
}
