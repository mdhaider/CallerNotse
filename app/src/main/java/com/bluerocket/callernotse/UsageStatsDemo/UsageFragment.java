package com.bluerocket.callernotse.UsageStatsDemo;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluerocket.callernotse.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class UsageFragment extends Fragment {

    long usedtime;
    List<AppUsageInfo> smallInfoList;
    long phoneUsageToday;
    public TextView launchCount;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_usage, container, false);

        launchCount= root.findViewById(R.id.launchCount);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUsageStatistics();

        for(AppUsageInfo info: smallInfoList){

            System.out.println(info.getPackageName()+info.getTimeInForeground()+"  "+info.launchCount+info.appName);
          // launchCount.setText(info.getLaunchCount());
        }





        System.out.println(String.valueOf(millsToDateFormat(phoneUsageToday)));

        UsageStatsManager usageStatsManager= (UsageStatsManager) getActivity().
                getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar c=Calendar.getInstance();


        List<UsageStats> usageStats= usageStatsManager.
                queryUsageStats(UsageStatsManager.INTERVAL_DAILY,System.currentTimeMillis()- TimeUnit.DAYS.toMillis(1),
                        System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(1));

        for(UsageStats usg: usageStats){
           int usgv= usageStats.size();
            Log.d("siz",String.valueOf(usgv));
            String packname=usg.getPackageName();
              usedtime=usg.getTotalTimeInForeground();
            //  System.out.println(packname+usedtime);

            long minutes = TimeUnit.MILLISECONDS.toMinutes(usedtime);
        }

      //  System.out.println(millsToDateFormat(usedtime));
    }

    public String millsToDateFormat(long mills) {

        Date date = new Date(mills);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(date);
        return dateFormatted; //note that it will give you the time in GMT+0
    }

    void getUsageStatistics() {

        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, AppUsageInfo> map = new HashMap <String, AppUsageInfo> ();

        long currTime = System.currentTimeMillis();
        long startTime =currTime - 1000*3600*3; //querying past three hours

        UsageStatsManager mUsageStatsManager =  (UsageStatsManager)
                getActivity().getSystemService(Context.USAGE_STATS_SERVICE);

        assert mUsageStatsManager != null;
        UsageEvents usageEvents = mUsageStatsManager.queryEvents(startTime, currTime);

//capturing all events in a array to compare with next element

        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                    currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                allEvents.add(currentEvent);
                String key = currentEvent.getPackageName();
// taking it into a collection to access by package name
                if (map.get(key)==null)
                    map.put(key,new AppUsageInfo(key));
            }
        }

//iterating through the arraylist
        for (int i=0;i<allEvents.size()-1;i++){
            UsageEvents.Event E0=allEvents.get(i);
            UsageEvents.Event E1=allEvents.get(i+1);

//for launchCount of apps in time range
            if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType()==1){
// if true, E1 (launch event of an app) app launched
                map.get(E1.getPackageName()).launchCount++;
            }

//for UsageTime of apps in time range
            if (E0.getEventType()==1 && E1.getEventType()==2
                    && E0.getClassName().equals(E1.getClassName())){
                long diff = E1.getTimeStamp()-E0.getTimeStamp();
                phoneUsageToday+=diff; //gloabl Long var for total usagetime in the timerange
                map.get(E0.getPackageName()).timeInForeground+= diff;
            }
        }
//transferred final data into modal class object
        smallInfoList = new ArrayList<>(map.values());

    }

}
