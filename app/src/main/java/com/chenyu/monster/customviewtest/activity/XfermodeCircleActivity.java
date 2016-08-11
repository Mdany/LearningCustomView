package com.chenyu.monster.customviewtest.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.view.circleImageView.DrawableCircleView;

/**
 * Created by chenyu on 16/8/11.
 */
public class XfermodeCircleActivity extends BaseActivity {
    @Override
    protected void viewDidLoad() {
        View view = View.inflate(this, R.layout.v_xfermode_circle, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.task);
        imageView.setImageDrawable(new DrawableCircleView(this, bitmap, DrawableCircleView.TYPE_ROUND));
        container.addView(view);
    }
}
