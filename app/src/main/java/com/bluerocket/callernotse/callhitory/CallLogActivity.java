package com.bluerocket.callernotse.callhitory;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.activities.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallLogActivity extends ListActivity {

    private static final int REQUEST_PERMISSION = 2002;
    private ArrayList<String> conNames;
    private ArrayList<String> conNumbers;
    private ArrayList<String> conTime;
    private ArrayList<String> conDate;
    private ArrayList<String> conType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        conNames = new ArrayList<String>();
        conNumbers = new ArrayList<String>();
        conTime = new ArrayList<String>();
        conDate = new ArrayList<String>();
        conType = new ArrayList<String>();

        Cursor curLog = CallLogHelper.getAllCallLogs(getContentResolver());

        setCallLogs(curLog);

        setListAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1,
                R.id.tvNameMain, conNames));
    }

    private void setCallLogs(Cursor curLog) {
        while (curLog.moveToNext()) {
            String callNumber = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.NUMBER));
            conNumbers.add(callNumber);

            String callName = curLog
                    .getString(curLog
                            .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            if (callName == null) {
                conNames.add("Unknown");
            } else
                conNames.add(callName);

            String callDate = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DATE));
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm");
            String dateString = formatter.format(new Date(Long
                    .parseLong(callDate)));
            conDate.add(dateString);

            String callType = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.TYPE));
            if (callType.equals("1")) {
                conType.add("Incoming");
            } else if (callType.equals("2")) {
                conType.add("Outgoing");
            } else if (callType.equals("3")) {
                conType.add("Missed");

            } else {
                conType.add("Rejected");
            }
            String duration = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DURATION));
            conTime.add(duration);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, HomeActivity.class));

    }

    private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<String> conNames) {
            super(context, resource, textViewResourceId, conNames);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = setList(position, parent);
            return row;
        }

        private View setList(int position, ViewGroup parent) {
            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inf.inflate(R.layout.liststyle, parent, false);

            TextView tvName = (TextView) row.findViewById(R.id.tvNameMain);
            TextView tvNumber = (TextView) row.findViewById(R.id.tvNumberMain);
            TextView tvTime = (TextView) row.findViewById(R.id.tvTime);
            TextView tvDate = (TextView) row.findViewById(R.id.tvDate);
            TextView tvType = (TextView) row.findViewById(R.id.tvType);

            tvName.setText(conNames.get(position));
            tvNumber.setText(conNumbers.get(position));
            tvTime.setText("( " + conTime.get(position) + "sec )");
            tvDate.setText(conDate.get(position));
            tvType.setText("( " + conType.get(position) + " )");

            return row;
        }
    }

}
