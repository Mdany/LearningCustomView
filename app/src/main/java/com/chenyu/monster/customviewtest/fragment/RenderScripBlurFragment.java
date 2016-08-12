package com.chenyu.monster.customviewtest.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.utils.RenderScripBlurHelper;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurFragment extends Fragment {
    private Activity mActivity;
    private Bitmap bitmap;
    private LinearLayout container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_render_scrip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (LinearLayout) view.findViewById(R.id.ll_layout);
        new BlurAsyncTask().execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    private class BlurAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            mActivity.getWindow().getDecorView().setDrawingCacheEnabled(true);
            bitmap = mActivity.getWindow().getDecorView().getDrawingCache();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return RenderScripBlurHelper.addColorToBitmap(
                    RenderScripBlurHelper.blur(mActivity, bitmap, 20, true),
                    mActivity.getResources().getColor(R.color.tran_black));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            container.setBackground(new BitmapDrawable(getResources(), bitmap));
        }
    }
}
