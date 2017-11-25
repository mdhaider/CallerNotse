package com.bluerocket.callernotse.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.bluerocket.callernotse.db.AppDatabase;
import com.bluerocket.callernotse.models.NoteModel;
import com.bluerocket.callernotse.services.CustomFloatingViewService;

import java.util.Date;
import java.util.List;

/**
 * Created by nehal on 11/24/2017.
 */

public class CallReceiver extends PhoneCallReceiver {
    private AppDatabase appDatabase;
    private  List<NoteModel> itemAndPersonList;

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                } else
                    context.startService(intent);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                context.stopService(intent);
            }
        }, 30000);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {

        context = ctx;

        final Intent intent = new Intent(context, CustomFloatingViewService.class);
        //  intent.putExtra("phone_no", number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.stopService(intent);

            }
        }, 500);
    }

    @Override
    protected void onOutgoingCallStarted(final Context ctx, String number, Date start) {
        //  Toast.makeText(ctx,"Nehal Incoming Call"+ number,Toast.LENGTH_LONG).show();

        context = ctx;

        final Intent intent = new Intent(context, CustomFloatingViewService.class);
        //  intent.putExtra("phone_no",number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                } else
                    context.startService(intent);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                context.stopService(intent);
            }
        }, 30000);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        // Toast.makeText(ctx,"Bye Bye"+ number,Toast.LENGTH_LONG).show();

        context = ctx;

        final Intent intent = new Intent(context, CustomFloatingViewService.class);
        //  intent.putExtra("phone_no", number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.stopService(intent);

            }
        }, 500);
    }

    @Override
    protected void onAsyncTaskCalled(Context ctx, String number) {
        //    Toast.makeText(ctx,"async task called"+ number,Toast.LENGTH_LONG).show();


      /*  public void deleteItem (NoteModel noteModel){
            new NoteListViewModel.deleteAsyncTask(appDatabase).execute(noteModel);
        }*/




          class compareAsyncTask extends AsyncTask<NoteModel, Void, Void> {

            private AppDatabase db;

            compareAsyncTask(AppDatabase appDatabase) {
                db = appDatabase;
            }

            @Override
            protected Void doInBackground(final NoteModel... params) {
                db.itemAndPersonModel().getAllNoteItems();
                Log.d("all","doinbackgroudrunning");
                return null;
            }

        }


       /* appDatabase = AppDatabase.getDatabase(ctx);
        itemAndPersonList = appDatabase.itemAndPersonModel().getAllNoteItems();

        for (NoteModel element : itemAndPersonList) {
            Date date = element.getBorrowDate();
            String phone = element.getItemName();
            String note = element.getPersonName();

            if (number.equalsIgnoreCase(phone)){
                Log.d("notes exist","yes");
            }
        }*/
    }


}


