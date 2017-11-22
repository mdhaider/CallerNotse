package com.bluerocket.callernotse.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.bluerocket.callernotse.db.AppDatabase;
import com.bluerocket.callernotse.models.NoteModel;

import java.util.List;


public class NoteListViewModel extends AndroidViewModel {

    private final LiveData<List<NoteModel>> itemAndPersonList;

    private AppDatabase appDatabase;

    public NoteListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndPersonList = appDatabase.itemAndPersonModel().getAllBorrowedItems();
    }


    public LiveData<List<NoteModel>> getItemAndPersonList() {
        return itemAndPersonList;
    }

    public void deleteItem(NoteModel noteModel) {
        new deleteAsyncTask(appDatabase).execute(noteModel);
    }

    private static class deleteAsyncTask extends AsyncTask<NoteModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final NoteModel... params) {
            db.itemAndPersonModel().deleteBorrow(params[0]);
            return null;
        }

    }

}
