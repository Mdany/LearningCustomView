package com.chenyu.monster.customviewtest.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.utils.RenderScripBlurHelper;
import com.chenyu.monster.customviewtest.view.BlurLinearLayout;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurFragment extends Fragment {
    private Activity mActivity;
    private Bitmap bitmap;
    private BlurLinearLayout container;

    private long start;
    private long end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        new BlurAsyncTask().execute();
        return inflater.inflate(R.layout.f_render_scrip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (BlurLinearLayout) view.findViewById(R.id.ll_layout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    private class BlurAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            start = System.currentTimeMillis();
            Log.i("fragment", "" + start);
            mActivity.getWindow().getDecorView().setDrawingCacheEnabled(true);
            bitmap = mActivity.getWindow().getDecorView().getDrawingCache();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return RenderScripBlurHelper.addColorToBitmap(
                    RenderScripBlurHelper.blur(mActivity, bitmap, RenderScripBlurHelper.MAX_BLUR_RADIUS),
                    mActivity.getResources().getColor(R.color.tran_black));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            container.setBackground(new BitmapDrawable(getResources(), bitmap));
            end = System.currentTimeMillis();
            Log.i("fragment", (end - start) + "");
        }
    }
}
