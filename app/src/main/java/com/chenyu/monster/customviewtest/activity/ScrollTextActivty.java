package com.chenyu.monster.customviewtest.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/5/31.
 */
public class ScrollTextActivty extends AppCompatActivity implements View.OnClickListener {
    private TextView content;
    private LinearLayout ll;
    private boolean canScroll = false;
    private int width, height;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_scroll_text);
        content = (TextView) findViewById(R.id.content);
        content.setText("张飞（？－221年），字益德[1]  ，幽州涿郡（今河北省保定市涿州市）人氏，三国时期蜀汉名将。刘备长坂坡败退，张飞仅率二十骑断后，据水断桥，曹军没人敢逼近；与诸葛亮、赵云扫荡西川时，于江州义释严颜；汉中之战时又于宕渠击败张郃，对蜀汉贡献极大，官至车骑将军、领司隶校尉，封西乡侯，后被范强、张达刺杀。后主时代追谥为“桓侯”。在中国传统文化中，张飞以其勇猛、鲁莽、嫉恶如仇而著称，虽然此形象主要来源于小说和戏剧等民间艺术，但已深入人心。");
        ll = (LinearLayout) findViewById(R.id.more);

        ll.setOnClickListener(this);

        width = content.getMeasuredWidth();
        height = content.getMeasuredHeight();
        int lines = content.getLineCount();
        int max = 3;
        if (lines > max) {//can scroll
            content.setMaxLines(max);
            content.setLines(max);
            canScroll = true;
        } else {//can't scroll
            canScroll = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.more) {
            if (canScroll) {
                scroll();
            } else {
                ellipse();
            }
        }
    }

    private void scroll() {
        ll.scrollTo(0, height);
    }

    private void ellipse() {
    }
}






















