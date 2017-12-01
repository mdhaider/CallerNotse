package com.bluerocket.callernotse.UsageStatsDemo;

import android.graphics.drawable.Drawable;

/**
 * Created by nehal on 11/28/2017.
 */

public class AppUsageInfo {
    Drawable appIcon;
    String appName, packageName;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getTimeInForeground() {
        return timeInForeground;
    }

    public void setTimeInForeground(long timeInForeground) {
        this.timeInForeground = timeInForeground;
    }

    public int getLaunchCount() {
        return launchCount;
    }

    public void setLaunchCount(int launchCount) {
        this.launchCount = launchCount;
    }

    long timeInForeground;
    int launchCount;

    AppUsageInfo(String pName) {
        this.packageName=pName;
    }

}
