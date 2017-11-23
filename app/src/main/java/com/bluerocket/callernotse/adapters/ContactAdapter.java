package com.bluerocket.callernotse.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.custom.RecyclerViewFastScroller;


/**
 * Created by brad on 2017/02/12.
 */

public class ContactAdapter extends CursorRecyclerViewAdapter<ContactAdapter.ContactsViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter {
    String username;
    public ContactAdapter(Context context, Cursor cursor, String id) {
        super(context, cursor, id);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder viewHolder, Cursor cursor) {
         username = cursor.getString(cursor.getColumnIndex(
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Data.DISPLAY_NAME_PRIMARY : ContactsContract.Data
                        .DISPLAY_NAME
        ));
        int totalcontact= cursor.getCount();
        Log.d("total contact list",String.valueOf(totalcontact));

        viewHolder.setUsername(username);
        long contactId = getItemId(cursor.getPosition());
        long photoId = cursor.getLong(cursor.getColumnIndex(
                ContactsContract.Data.PHOTO_ID
        ));

        if (photoId != 0) {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                    contactId);
            Uri photUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo
                    .CONTENT_DIRECTORY);
            viewHolder.imageViewContactDisplay.setImageURI(photUri);
        } else
            viewHolder.imageViewContactDisplay.setImageResource(R.drawable.facebook_avatar);
    }

    @Override
    public String getTextToShowInBubble(final int pos) {

        return Character.toString(username.charAt(0));
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContactUsername;
        ImageView imageViewContactDisplay;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            textViewContactUsername = (TextView) itemView.findViewById(R.id
                    .text_view_contact_username);
            imageViewContactDisplay = (ImageView) itemView.findViewById(R.id
                    .image_view_contact_display);
        }

        public void setUsername(String username) {
            textViewContactUsername.setText(username);
        }
    }
}
