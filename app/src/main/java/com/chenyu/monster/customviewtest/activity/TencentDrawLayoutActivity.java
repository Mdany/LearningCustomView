package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.view.tencentDrawLayout.CustomDrawerLayout;

public class TencentDrawLayoutActivity extends AppCompatActivity {
    private ImageButton mLeftTopBtn;
    private CustomDrawerLayout mTencentContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drage_helper);

        mTencentContentLayout = (CustomDrawerLayout) findViewById(R.id.main_layout_id);
        mLeftTopBtn = (ImageButton) findViewById(R.id.left_top_btn);
        mLeftTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTencentContentLayout.isLeftIsOpened()) {
                    mTencentContentLayout.closeLeftView();
                }
                else {
                    mTencentContentLayout.openLeftView();
                }
            }
        });
    }
}
