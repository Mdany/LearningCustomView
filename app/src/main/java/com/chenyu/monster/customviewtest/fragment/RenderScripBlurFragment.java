package com.chenyu.monster.customviewtest.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.utils.RenderScripBlurHelper;
import com.chenyu.monster.customviewtest.view.BlurImageView;

/**
 * Created by chenyu on 16/8/12.
 */
public class RenderScripBlurFragment extends Fragment {
    private Activity mActivity;
    private Bitmap bitmap;
    private FrameLayout container;
    private BlurImageView back;

    private long start;
    private long end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_render_scrip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (FrameLayout) view.findViewById(R.id.ll_layout);
        container.addView(back, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        back = (BlurImageView) View.inflate(mActivity, R.layout.f_render_scripe_back, null);
        mActivity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        back.setImageBitmap(mActivity.getWindow().getDecorView().getDrawingCache());
    }

    private class BlurAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            start = System.currentTimeMillis();
            Log.i("fragment", "" + start);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return RenderScripBlurHelper.addColorToBitmap(
                    RenderScripBlurHelper.blur(mActivity, bitmap, RenderScripBlurHelper.MAX_BLUR_RADIUS),
                    mActivity.getResources().getColor(R.color.tran_black));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            back.setImageBitmap(bitmap);
            end = System.currentTimeMillis();
            Log.i("fragment", (end - start) + "");
        }
    }
}
