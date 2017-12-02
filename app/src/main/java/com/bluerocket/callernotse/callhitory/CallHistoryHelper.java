package com.bluerocket.callernotse.callhitory;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

/**
 * Created by nehal on 12/1/2017.
 */

public class CallHistoryHelper {

    public static Cursor getAllCallLogs(ContentResolver cr){
        Uri callUri = Uri.parse("content://call_log/calls");
        String callSortOrder = CallLog.Calls.DATE + " DESC";

        Cursor callCursor = cr.query(callUri,null,null,null,callSortOrder);

        return callCursor;

    }
}
