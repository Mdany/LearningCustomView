package com.chenyu.monster.customviewtest.activity;

import com.chenyu.monster.customviewtest.view.EraserView;

/**
 * Created by chenyu on 16/3/22.
 */
public class EraseActivity extends BaseActivity {
    private EraserView eraserView;

    @Override
    protected void viewDidLoad() {
        eraserView = new EraserView(this);
        addView(eraserView);
    }
}
