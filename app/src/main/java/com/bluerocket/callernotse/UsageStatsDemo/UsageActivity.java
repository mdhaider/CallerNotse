package com.bluerocket.callernotse.UsageStatsDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bluerocket.callernotse.R;

import java.util.concurrent.TimeUnit;

public class UsageActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        long val=TimeUnit.DAYS.toMillis(1);
      //  Log.d("hrs",String.valueOf(val));

        getSupportFragmentManager().beginTransaction().add(R.id.containerfrags,new UsageFragment()).commit();
    }
}
