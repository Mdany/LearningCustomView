package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.fragment.ViewPagerFragment;
import com.chenyu.monster.customviewtest.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 16/5/25.
 */
public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mDatas;
    private int itemCount = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_viewpager);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mList = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            Fragment fragment = new ViewPagerFragment();
            mList.add(fragment);
        }

        mDatas = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mDatas.add("i=" + i);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);

    }
}
