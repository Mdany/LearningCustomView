package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/3/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected LinearLayout container;
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        viewDidLoad();
    }

    protected void initView(){
        setContentView(R.layout.a_show);
        container = (LinearLayout) findViewById(R.id.container);
    }

    protected abstract void viewDidLoad();

    protected void addView(View v) {
        container.addView(v);
    }
}
