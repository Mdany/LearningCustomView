package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenyu.monster.customviewtest.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

/**
 * Created by chenyu on 16/9/23.
 */

public class FloatingActionButton extends LinearLayout {
    private static final int DEFAULT_ADD_ICON = -1;
    private View contentView;
    private TextView title;
    private ImageView icon;

    private String itemTitle;
    private int itemIcon;

    private int mWidth, mHeight;

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initView(context);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton);
        itemTitle = array.getString(R.styleable.FloatingActionButton_title);
        itemIcon = array.getResourceId(R.styleable.FloatingActionButton_icon, DEFAULT_ADD_ICON);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private void initView(Context context) {
        contentView = inflate(context, R.layout.floating_action_button, null);
        title = (TextView) contentView.findViewById(R.id.title_tv);
        icon = (ImageView) contentView.findViewById(R.id.icon_iv);

        if (!TextUtils.isEmpty(itemTitle)) {
            title.setText(itemTitle);
            title.setVisibility(VISIBLE);
        }

        if (itemIcon != DEFAULT_ADD_ICON) {
            icon.setImageResource(itemIcon);
            icon.setVisibility(VISIBLE);
        }

        addView(contentView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidth = r - l;
        mHeight = b - t;
    }

    /**
     * 获取title
     * @return
     */
    public String getTitle() {
        return itemTitle;
    }

    public void show(int position) {
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", mHeight * position, 0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(contentView, translateY).setDuration(300).start();
        ObjectAnimator.ofPropertyValuesHolder(contentView, alpha).setDuration(400).start();
    }

    public void hide(int position) {
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", 0f, mHeight * position);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator.ofPropertyValuesHolder(contentView, translateY).setDuration(300).start();
        ObjectAnimator.ofPropertyValuesHolder(contentView, alpha).setDuration(400).start();
    }

//    public void show() {
//        PropertyValuesHolder pivotX = PropertyValuesHolder.ofFloat("pivotX", title.getRight());
//        PropertyValuesHolder pivotY = PropertyValuesHolder.ofFloat("pivotY", title.getBottom());
//
//        PropertyValuesHolder translateX = PropertyValuesHolder.ofFloat("translationX", title.getRight(), 0);
//
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
//
//        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
//
//        ObjectAnimator.ofPropertyValuesHolder(title, pivotX, pivotY, translateX).setDuration(300).start();
//        ObjectAnimator.ofPropertyValuesHolder(title, alpha).setDuration(200).start();
//        ObjectAnimator.ofPropertyValuesHolder(icon, scaleX, scaleY, alpha).setDuration(300).start();
//    }
//
//    public void hide() {
//        PropertyValuesHolder pivotX = PropertyValuesHolder.ofFloat("pivotX", title.getRight());
//        PropertyValuesHolder pivotY = PropertyValuesHolder.ofFloat("pivotY", title.getBottom());
//
//        PropertyValuesHolder translateX = PropertyValuesHolder.ofFloat("translationX", 0, title.getRight());
//
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
//
//        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
//
//        ObjectAnimator.ofPropertyValuesHolder(title, pivotX, pivotY, translateX).setDuration(300).start();
//        ObjectAnimator.ofPropertyValuesHolder(title, alpha).setDuration(200).start();
//        ObjectAnimator.ofPropertyValuesHolder(icon, scaleX, scaleY, alpha).setDuration(300).start();
//    }
}
