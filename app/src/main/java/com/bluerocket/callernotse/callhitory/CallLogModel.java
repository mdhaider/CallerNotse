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
    private String conType;
    private String conDate;

    public String getDuration() {
        return duration;
    }

    private String duration;

    public CallLogModel(String conName, String conNumber,String conDate,String conType,String duration) {
        this.conName = conName;
        this.conNumber = conNumber;
        this.conType = conType;
        this.conDate = conDate;
        this.duration = duration;
    }
    public String getConName() {
        return conName;
    }

    public String getConNumber() {
        return conNumber;
    }

    public String getConDate() {
        return conDate;
    }

    public String getConType() {
        return conType;
    }
}

