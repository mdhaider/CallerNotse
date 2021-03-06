package com.bluerocket.callernotse.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Date;

public class PhoneCallReceiver extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static boolean isOutgoing;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            //to Fix Lolipop broadcast issue.
            long subId = intent.getLongExtra("subscription", Long.MIN_VALUE);
            if (subId < Integer.MAX_VALUE) {
                if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                    savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
                   onAsyncTaskCalled(context ,savedNumber);

               /* isOutgoing=true;*/
                    //  Toast.makeText(context,"You are calling   " +savedNumber,Toast.LENGTH_SHORT).show();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);

                } else {
                    String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                    String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    onAsyncTaskCalled(context ,savedNumber);
                    int state = 0;
                    if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        state = TelephonyManager.CALL_STATE_IDLE;
                    } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        state = TelephonyManager.CALL_STATE_OFFHOOK;
                    } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        state = TelephonyManager.CALL_STATE_RINGING;
                    }

                    onCallStateChanged(context, state, number);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, Date start){}
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onOutgoingCallStarted(Context ctx, String number, Date start){}
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end){}
    protected void onAsyncTaskCalled(Context ctx, String number){}

    public void onCallStateChanged(Context context, int state, String number)
    {
        if(lastState == state)
        {
            //No change, debounce extras
            return;
        }
        switch (state)
        {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = true;
                    callStartTime = new Date();
                   // onReceiveCall(context, savedNumber, callStartTime, "received");
                } else {
                    isIncoming = false;
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {

                //    onMissedCall(context, savedNumber, callStartTime, "Missed");
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else {
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
        lastState = state;
    }
}