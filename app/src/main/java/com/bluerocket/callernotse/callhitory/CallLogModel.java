package com.bluerocket.callernotse.callhitory;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nehal on 12/1/2017.
 */

@Entity
public class CallLogModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String conName;
    private String conNumber;
    private String conTime;
    private String conDate;
    private String conType;

    public CallLogModel(String conName, String conNumber,String conType,String conDate,String conTime) {
        this.conName = conName;
        this.conNumber = conName;
        this.conType = conType;
        this.conDate = conDate;
        this.conTime = conTime;
    }
    public String getConName() {
        return conName;
    }

    public String getConNumber() {
        return conNumber;
    }

    public String getConTime() {
        return conTime;
    }

    public String getConDate() {
        return conDate;
    }

    public String getConType() {
        return conType;
    }
}

