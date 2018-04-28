package com.jiakaiyang.xradiogroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jiakaiyang.xradiogroup.lib.XRadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clearCheck(View view) {
        XRadioGroup group = (XRadioGroup) findViewById(R.id.mGroup);
        group.clearCheck();
    }
}
