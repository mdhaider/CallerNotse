/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.bluerocket.callernotse.appusage;

import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bluerocket.callernotse.R;

import java.util.List;

/**
 * Launcher Activity for the App Usage Statistics sample app.
 */
public class AppUsageStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_usage_statistics);
        UsageStatsManager usageStatsManager;
        installedApps();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, AppUsageStatisticsFragment.newInstance())
                    .commit();
        }
    }

    public  void installedApps()
    {
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Log.e("App" + Integer.toString(i), appName);
            }
        }
    }
}
