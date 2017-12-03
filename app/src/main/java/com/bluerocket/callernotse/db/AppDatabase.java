package com.bluerocket.callernotse.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bluerocket.callernotse.callhitory.AddCallLogistoryModelDao;
import com.bluerocket.callernotse.callhitory.CallLogModel;
import com.bluerocket.callernotse.models.NoteModel;

@Database(entities = {NoteModel.class, CallLogModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "note_db")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract NoteModelDao itemAndPersonModel();
    public abstract AddCallLogistoryModelDao addCallLogistoryModelDao();

}
