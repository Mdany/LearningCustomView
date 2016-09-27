package com.chenyu.monster.customviewtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenyu.monster.customviewtest.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;


/**
 * Created by chenyu on 16/9/22.
 */

public class FloatingActionMenu extends LinearLayout {
    private static final int DEFAULT_ADD_ICON = -1;
    private String title;
    private int icon;
    private Drawable parentBackground;

    private LinearLayout parent;
    private TextView addTv;

    /**
     * 记录当前是否打开
     */
    private boolean isOpen;

    private boolean isAdded;

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initView(context);
    }

    private void init(Context context, AttributeSet attrs) {
        isOpen = false;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionMenu);
        title = array.getString(R.styleable.FloatingActionMenu_addText);
        icon = array.getResourceId(R.styleable.FloatingActionMenu_addIcon, DEFAULT_ADD_ICON);

        parentBackground = array.getDrawable(R.styleable.FloatingActionMenu_pressDrawable);
    }

    private void initView(Context context) {
        parent = new LinearLayout(context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(llp);
        parent.setBackground(parentBackground);

        addTv = (TextView) inflate(context, R.layout.floating_action_menu_button, null);
        if (!TextUtils.isEmpty(title)) {
            addTv.setText(title);
        } else if (icon != DEFAULT_ADD_ICON) {
            addTv.setBackgroundResource(icon);
        }

        parent.addView(addTv);
        parent.setGravity(Gravity.CENTER);
        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (icon != DEFAULT_ADD_ICON) {
                    resetButton();
                }
                resetChild();
                isOpen = !isOpen;
            }
        });

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(VERTICAL);

        setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (child instanceof FloatingActionButton)
                    child.setVisibility(INVISIBLE);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isAdded) {
            addView(parent);
            isAdded = true;
        }
    }

    /**
     * 如果是icon就用旋转的方式
     */
    private void resetButton() {
        if (isOpen) {
            ObjectAnimator.ofFloat(addTv, "rotation", 90f, 0f)
                    .setDuration(300)
                    .start();
        } else {
            ObjectAnimator.ofFloat(addTv, "rotation", 0, 90f)
                    .setDuration(300)
                    .start();
        }
    }

    private void resetChild() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof FloatingActionButton) {
                FloatingActionButton button = (FloatingActionButton) getChildAt(i);
                if (button.getVisibility() == INVISIBLE) {
                    button.setVisibility(VISIBLE);
                }
                if (isOpen)
//                    button.hide(getChildCount() - i);
                    hide(getChildCount() - i - 1, button);
                else
//                    button.show(getChildCount() - i);
                    show(getChildCount() - i - 1, button);
            }
        }
    }

    public void show(int position, View target) {
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", target.getHeight() * position, 0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(target, translateY).setDuration(300).start();
        ObjectAnimator.ofPropertyValuesHolder(target, alpha).setDuration(200).start();
    }

    public void hide(int position, View target) {
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", 0f, target.getHeight() * position);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator.ofPropertyValuesHolder(target, translateY).setDuration(300).start();
        ObjectAnimator.ofPropertyValuesHolder(target, alpha).setDuration(200).start();
    }
}
