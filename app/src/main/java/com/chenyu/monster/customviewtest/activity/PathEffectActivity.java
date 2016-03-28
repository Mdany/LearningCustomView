package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.PathEffectView;

/**
 * Created by chenyu on 16/3/28.
 */
public class PathEffectActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        container.addView(new PathEffectView(this));
    }
}
