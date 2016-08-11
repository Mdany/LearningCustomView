package com.chenyu.monster.customviewtest.activity;

import android.view.View;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/8/11.
 */
public class XfermodeCircleActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(View.inflate(this, R.layout.v_xfermode_circle, null));
    }
}
