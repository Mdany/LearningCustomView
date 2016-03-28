package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.MaskFilterView;

/**
 * Created by chenyu on 16/3/28.
 */
public class MaskFillterActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        MaskFilterView view = new MaskFilterView(this);
        container.addView(view);
    }
}
