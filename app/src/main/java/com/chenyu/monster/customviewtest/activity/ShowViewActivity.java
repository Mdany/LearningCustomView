package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.view.ColorMatrixView;

/**
 * Created by chenyu on 16/3/16.
 */
public class ShowViewActivity extends AppCompatActivity {
    private LinearLayout container;
    private ColorMatrixView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_show);

        customView = new ColorMatrixView(this);
        container = (LinearLayout) findViewById(R.id.container);
        container.addView(customView);
    }
}
