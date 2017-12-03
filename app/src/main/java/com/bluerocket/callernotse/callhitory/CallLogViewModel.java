package com.bluerocket.callernotse.callhitory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.bluerocket.callernotse.db.AppDatabase;

import java.util.List;


public class CallLogViewModel extends AndroidViewModel {

    private final LiveData<List<CallLogModel>> callLogList;

    private AppDatabase appDatabase;

    public CallLogViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        callLogList = appDatabase.addCallLogistoryModelDao().getAllCallLogs();
    }


    public LiveData<List<CallLogModel>> getCallLogList() {
        return callLogList;

    }

}
