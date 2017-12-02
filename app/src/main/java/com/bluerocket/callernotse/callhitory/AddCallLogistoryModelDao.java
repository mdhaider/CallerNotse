package com.bluerocket.callernotse.callhitory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.bluerocket.callernotse.converter.DateConverter;
import com.bluerocket.callernotse.models.NoteModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface AddCallLogistoryModelDao {

    @Query("select * from NoteModel")
    LiveData<List<NoteModel>> getAllBorrowedItems();

    @Query("select * from NoteModel")
    List<NoteModel> getAllNoteItems();

    @Query("select * from NoteModel where id = :id")
    NoteModel getItembyId(String id);

    @Insert(onConflict = REPLACE)
    void addBorrow(NoteModel noteModel);

    @Delete
    void deleteBorrow(NoteModel noteModel);

}
