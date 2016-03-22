package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.view.EraserView;

/**
 * Created by chenyu on 16/3/22.
 */
public class EraseActivity extends AppCompatActivity {
    private LinearLayout container;
    private EraserView eraserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_show);

        container = (LinearLayout) findViewById(R.id.container);
        eraserView = new EraserView(this);
        container.addView(eraserView);
    }
}
