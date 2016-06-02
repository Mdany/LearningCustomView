package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by chenyu on 16/5/31.
 */
public class SeeMoreTextView extends LinearLayout {
    private Context context;

    public SeeMoreTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SeeMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SeeMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public SeeMoreTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init(){

    }
}





















