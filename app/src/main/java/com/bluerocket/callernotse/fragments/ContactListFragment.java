package com.bluerocket.callernotse.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluerocket.callernotse.BuildConfig;
import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.adapters.ContactAdapter;
import com.bluerocket.callernotse.custom.RecyclerViewFastScroller;


/**
 * Created by nehal on 11/23/2017.
 */

public class ContactListFragment extends Fragment implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private RecyclerView mWhatsappRecycler;
    private ContactAdapter mContactAdapter;
    private static final int REQUEST_PERMISSION = 2001;
    private View mRootView;

    private static final String[] FROM_COLUMNS = {
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data
                    .DISPLAY_NAME,
            ContactsContract.Data.HAS_PHONE_NUMBER,
            ContactsContract.Data.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    private Bundle mBundle;

    public ContactListFragment() {
    }


    public static ContactListFragment newInstance() {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.whatsapp_recycler);

        mBundle = savedInstanceState;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{
                            Manifest.permission.READ_CONTACTS
                    },
                    REQUEST_PERMISSION
            );
        } else {
            getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
        }
        init();

        final RecyclerViewFastScroller fastScroller = (RecyclerViewFastScroller) mRootView.findViewById(R.id.fastscroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(final RecyclerView.Recycler recycler, final RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                //TODO if the items are filtered, considered hiding the fast scroller here
                final int firstVisibleItemPosition = findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    // this avoids trying to handle un-needed calls
                    if (firstVisibleItemPosition == -1)
                        //not initialized, or no items shown, so hide fast-scroller
                        fastScroller.setVisibility(View.GONE);
                    return;
                }
                final int lastVisibleItemPosition = findLastVisibleItemPosition();
                int itemsShown = lastVisibleItemPosition - firstVisibleItemPosition + 1;
                //if all items are shown, hide the fast-scroller
                fastScroller.setVisibility(mContactAdapter.getItemCount() > itemsShown ? View.VISIBLE : View.GONE);
            }
        });
        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setViewsToUse(R.layout.recycler_view_fast_scroller__fast_scroller, R.id.fastscroller_bubble, R.id.fastscroller_handle);

        return mRootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length != 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLoaderManager().initLoader(LOADER_ID, mBundle, this);
                } else {
                    getActivity().finish();
                }
            }
        }
    }

    private void init() {
        mWhatsappRecycler = (RecyclerView) mRootView.findViewById(R.id.whatsapp_recycler);
        mWhatsappRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mWhatsappRecycler.setHasFixedSize(true);

        mContactAdapter = new ContactAdapter(getContext(), null, ContactsContract.Data
                .CONTACT_ID);

        mContactAdapter.setOnRecyclerViewItemClickListener(new ContactAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(String text,String text2) {
                Log.d("TAG", "Text is = " + text+" "+text2);

                Bundle bundle= new Bundle();
                bundle.putString("name", text2);
                bundle.putString("phone", text);

                Intent returnIntent = new Intent();
                returnIntent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK, returnIntent);
                getActivity().finish();
            }
        });
        mWhatsappRecycler.setAdapter(mContactAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        getContext(),
                        ContactsContract.Data.CONTENT_URI,
                        FROM_COLUMNS,
                        null,
                        null,
                        (ContactsContract.Data
                                .DISPLAY_NAME) +
                                " ASC"
                );
            default:
                if (BuildConfig.DEBUG)
                    throw new IllegalArgumentException("no id handled!");
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mContactAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mContactAdapter.swapCursor(null);
    }
}

