package com.bluerocket.callernotse.callhitory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.bluerocket.callernotse.converter.DateConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface AddCallLogistoryModelDao {

    @Query("select * from CallLogModel")
    LiveData<List<CallLogModel>> getAllCallLogs();

    @Query("select * from CallLogModel where id = :id")
    CallLogModel getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addCallLogs(CallLogModel callLogModel);

}
