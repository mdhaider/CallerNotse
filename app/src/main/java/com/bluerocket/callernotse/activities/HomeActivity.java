package com.bluerocket.callernotse.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.adapters.RecyclerViewAdapter;
import com.bluerocket.callernotse.models.NoteModel;
import com.bluerocket.callernotse.viewmodel.NoteListViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnLongClickListener,BottomNavigationBar.OnTabSelectedListener {

    private NoteListViewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_LOGS = 123;

    private static final long RIPPLE_DURATION = 200;
    private static final long SET_DURATION = 1000;



   private Toolbar toolbar;

   private CoordinatorLayout root;

   private View contentHamburger;
    Context context;
    BottomNavigationBar bottomNavigationBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        checkPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String channelId = getString(R.string.default_floatingview_channel_id);
            final String channelName = getString(R.string.default_floatingview_channel_name);
            final NotificationChannel defaultChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(defaultChannel);
            }
        }
    //    toolbar=findViewById(R.id.toolbar);
        root=findViewById(R.id.root);
        FloatingActionButton fab = findViewById(R.id.fab);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

/*

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddNoteActivity.class));
            }
        });

        bottomNavigationBar.setFab(fab);
        bottomNavigationBar.setMode(1);
        bottomNavigationBar.setBackgroundStyle(2);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_action_search, "Home").setActiveColorResource(R.color.primary))
                .addItem(new BottomNavigationItem(R.drawable.ic_done_white_24dp, "Books").setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.ic_add_white_24dp, "Music").setActiveColorResource(R.color.selected_item_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_done_white_24dp, "Movies & TV").setActiveColorResource(R.color.lightorange))
                .setFirstSelectedPosition(0)
                .initialise();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<NoteModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel.class);

        viewModel.getItemAndPersonList().observe(HomeActivity.this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> itemAndPeople) {
                recyclerViewAdapter.addItems(itemAndPeople);
            }
        });

    }

    @Override
    public boolean onLongClick(View v) {
        NoteModel noteModel = (NoteModel) v.getTag();
        viewModel.deleteItem(noteModel);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_CALL_LOG)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_LOGS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_WRITE_LOGS);
                }
                return;
            } else {
                return ;
            }
        } else {
            return ;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_LOGS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 //   writeCalendarEvent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onTabSelected(int position) {

     //   Toast.makeText(this,"Under Construction"+position,Toast.LENGTH_SHORT).setGravity(););

      //  setScrollableText(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
        setMessageText(position + " Tab Reselected");
    }

    private void setMessageText(String messageText) {
     //   message.setText(messageText);
    }

    private void setScrollableText(int position) {
        switch (position) {
            case 0:
           //     getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment1).commitAllowingStateLoss();
           Toast.makeText(this,"Under Construction",Toast.LENGTH_SHORT).setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                break;
            case 1:
            //    getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment2).commitAllowingStateLoss();
                break;
        }
    }
}

