package com.bluerocket.callernotse.callhitory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.bluerocket.callernotse.db.AppDatabase;


public class AddCallHistoryViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddCallHistoryViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addCallLogs(final CallLogModel callLogModel) {
        new addAsyncTask(appDatabase).execute(callLogModel);
    }

    private static class addAsyncTask extends AsyncTask<CallLogModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(CallLogModel... callLogModels) {
            db.addCallLogistoryModelDao().addCallLogs(callLogModels[0]);
            return null;
        }
    }
}
