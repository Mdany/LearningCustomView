package com.chenyu.monster.customviewtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyu.monster.customviewtest.R;
import com.chenyu.monster.customviewtest.view.FloatingActionButton;

/**
 * Created by chenyu on 16/9/23.
 */

public class FloatingActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private TextView cs;
    private FloatingActionButton fab1,fab2,fab3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_floating);

        context = this;

        cs = (TextView) findViewById(R.id.cs_btn);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        cs.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab1:
                Toast.makeText(context, fab1.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                Toast.makeText(context, fab2.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab3:
                Toast.makeText(context, fab3.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.cs_btn:
                Toast.makeText(context, "hell word", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
