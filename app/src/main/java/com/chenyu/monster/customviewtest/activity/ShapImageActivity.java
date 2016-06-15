package com.chenyu.monster.customviewtest.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chenyu.monster.customviewtest.R;

/**
 * Created by chenyu on 16/6/14.
 */
public class ShapImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class Task1 extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap result = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.task);

            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(0, 0, 500, 500);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setFilterBitmap(true);

            paint.setXfermode(null);
            canvas.drawRoundRect(rectF, 8, 8, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rectF, paint);
            return null;
        }
    }

    class Task2 extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap result = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.task);

            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(0, 0, 500, 500);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setFilterBitmap(true);

            Path path = new Path();
            path.addRoundRect(rectF, 8, 8, Path.Direction.CW);
            canvas.clipPath(path, Region.Op.INTERSECT);
            canvas.drawBitmap(bitmap, rect, rectF, paint);
            return null;
        }
    }
}
