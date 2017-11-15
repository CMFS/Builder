package com.cmfs.builder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmfs.builder.bean.Model;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model model = BuilderBundle
                .newModelBuilder()
                .str2("String2")
                .build();

        Log.d(TAG, "onCreate: " + model.getStr2());
    }
}
