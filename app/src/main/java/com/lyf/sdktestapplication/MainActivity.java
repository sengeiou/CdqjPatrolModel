package com.lyf.sdktestapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_tv).setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            CdqjInitDataConfig.initPatrolData(this, "008", "008");
        });
    }
}
