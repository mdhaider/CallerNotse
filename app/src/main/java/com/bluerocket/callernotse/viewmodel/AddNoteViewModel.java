package com.bluerocket.callernotse.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.bluerocket.callernotse.db.AppDatabase;
import com.bluerocket.callernotse.models.NoteModel;

public class AddNoteViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddNoteViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addBorrow(final NoteModel noteModel) {
        new addAsyncTask(appDatabase).execute(noteModel);
    }

    private static class addAsyncTask extends AsyncTask<NoteModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final NoteModel... params) {
            db.itemAndPersonModel().addBorrow(params[0]);
            return null;
        }

    }
}
