package com.bluerocket.callernotse.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.bluerocket.callernotse.services.CustomFloatingViewService;

import java.util.Date;

/**
 * Created by nehal on 11/24/2017.
 */

public class CallReceiver extends PhoneCallReceiver {


    Context context;

    @Override
    protected void onIncomingCallStarted(final Context ctx, String number, Date start) {
       // Toast.makeText(ctx, "Nehal Incoming Call" + number, Toast.LENGTH_LONG).show();


        context = ctx;

        final Intent intent = new Intent(context, CustomFloatingViewService.class);
      //  intent.putExtra("phone_no", number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    context.startForegroundService(intent);
            }else
                context.startService(intent);
            }
        }, 1000);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        // Toast.makeText(ctx,"Bye Bye"+ number,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onOutgoingCallStarted(final Context ctx, String number, Date start)
    {
        //  Toast.makeText(ctx,"Nehal Incoming Call"+ number,Toast.LENGTH_LONG).show();

        context =   ctx;

        final Intent intent = new Intent(context, CustomFloatingViewService.class);
      //  intent.putExtra("phone_no",number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    context.startForegroundService(intent);
                }else
                    context.startService(intent);
            }
        }, 1000);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
         Toast.makeText(ctx,"Bye Bye"+ number,Toast.LENGTH_LONG).show();
    }
}


