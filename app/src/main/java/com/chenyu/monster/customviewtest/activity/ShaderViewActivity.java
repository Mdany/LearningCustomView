package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.ShaderView;

/**
 * Created by chenyu on 16/3/30.
 */
public class ShaderViewActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new ShaderView(this));
    }
}
