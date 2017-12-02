package com.bluerocket.callernotse.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.callhitory.CallLogActivity;
import com.bluerocket.callernotse.fragments.AddNoteFragment;
import com.bluerocket.callernotse.fragments.CallerNotesListFragment;
import com.bluerocket.callernotse.fragments.HomeFragment;
import com.bluerocket.callernotse.widget.Fab;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, HomeFragment.OnFragmentInteractionListener, BottomNavigationBar.OnTabSelectedListener, AddNoteFragment.OnFragmentInteractionListener {

    public Fab fab;
    @Nullable
    TextBadgeItem numberBadgeItem;
    BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupFab();
        setupBottomDrawer();

        setupNotificationCannel();
        materialSheetFab.showFab();

    }

    private void setupNotificationCannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String channelId = getString(R.string.default_floatingview_channel_id);
            final String channelName = getString(R.string.default_floatingview_channel_name);
            final NotificationChannel defaultChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(defaultChannel);
            }
        }

    }

    public void setupBottomDrawer() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.clearAll();

        setScrollableText(lastSelectedPosition);

        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + 0)
                .setHideOnSelect(true);

        bottomNavigationBar.setFab(fab);
        bottomNavigationBar.setMode(0);
        bottomNavigationBar.setBackgroundStyle(1);
        bottomNavigationBar.setTabSelectedListener(this);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.primary))
                .addItem(new BottomNavigationItem(R.drawable.ic_list_white_24dp, "List").setActiveColorResource(R.color.guillotine_background).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_archive_white_24dp, "Archive").setActiveColorResource(R.color.selected_item_color))
                .addItem(new BottomNavigationItem(R.drawable.ic_settings_white_24dp, "Settings").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(lastSelectedPosition > 0 ? lastSelectedPosition : 0)
                .initialise();

    }

    @Override
    public void onTabSelected(int position) {

        lastSelectedPosition = position;
        setMessageText(position + " Tab Selected");
        if (numberBadgeItem != null) {
            numberBadgeItem.setText(Integer.toString(position));
        }
        setScrollableText(position);

    }

    private void setScrollableText(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commitAllowingStateLoss();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CallerNotesListFragment()).commitAllowingStateLoss();
                break;
            case 2:
                startActivity(new Intent(this, CallLogActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, SettingsPrefActivity.class));
                break;
        }
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    public void setupFab() {

        fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);


        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_accent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        findViewById(R.id.fab_sheet_item_recording).setOnClickListener(HomeActivity.this);
        findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners

    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddNoteFragment()).commitAllowingStateLoss();
        materialSheetFab.hideSheet();
    }
}

