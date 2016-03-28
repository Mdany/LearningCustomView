package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.StaticLayoutVIew;

/**
 * Created by chenyu on 16/3/23.
 * 文本换行view   TextPaint and StaticLayout
 */
public class StaticLayoutActivity extends BaseActivity {
    private StaticLayoutVIew mStaticView;

    @Override
    protected void viewDidLoad() {
        mStaticView = new StaticLayoutVIew(this);
        addView(mStaticView);
    }
}
