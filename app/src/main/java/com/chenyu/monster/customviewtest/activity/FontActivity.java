package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.FontView;

/**
 * Created by chenyu on 16/3/22.
 */
public class FontActivity extends BaseActivity {
    private FontView fontView;

    @Override
    protected void viewDidLoad() {
        fontView = new FontView(this);
        addView(fontView);
    }
}
