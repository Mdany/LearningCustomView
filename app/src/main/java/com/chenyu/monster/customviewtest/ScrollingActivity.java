package com.chenyu.monster.customviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chenyu.monster.customviewtest.activity.ECGViewActivity;
import com.chenyu.monster.customviewtest.activity.EraseActivity;
import com.chenyu.monster.customviewtest.activity.FontActivity;
import com.chenyu.monster.customviewtest.activity.MaskFillterActivity;
import com.chenyu.monster.customviewtest.activity.PathEffectActivity;
import com.chenyu.monster.customviewtest.activity.ShowViewActivity;
import com.chenyu.monster.customviewtest.activity.StaticLayoutActivity;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.show_view_activity) {
            pushActivity(ShowViewActivity.class);
            return true;
        } else if (id == R.id.erase_view_activity) {
            pushActivity(EraseActivity.class);
            return true;
        } else if (id == R.id.font_view_activity) {
            pushActivity(FontActivity.class);
            return true;
        } else if (id == R.id.static_view_activity) {
            pushActivity(StaticLayoutActivity.class);
            return true;
        } else if (id == R.id.mask_view_activity) {
            pushActivity(MaskFillterActivity.class);
            return true;
        } else if (id == R.id.path_view_activity) {
            pushActivity(PathEffectActivity.class);
            return true;
        } else if (id == R.id.ecg_view_activity) {
            pushActivity(ECGViewActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pushActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
