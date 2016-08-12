package com.chenyu.monster.customviewtest.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.fragment.RenderScripBlurFragment;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurActivity extends BaseActivity {
    FrameLayout container;
    ImageView avatar;

    @Override
    protected void initView() {
        setContentView(R.layout.a_render_scrip_blur);
        container = (FrameLayout) findViewById(R.id.frame_layout);
        avatar = (ImageView) findViewById(R.id.avatar);
    }

    @Override
    protected void viewDidLoad() {
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout,
                                RenderScripBlurFragment
                                        .instantiate(RenderScripBlurActivity.this,
                                                RenderScripBlurFragment.class.getName()))
                        .commit();
            }
        });
    }
}
