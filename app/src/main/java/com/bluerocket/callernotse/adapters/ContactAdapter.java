package com.bluerocket.callernotse.adapters;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.widget.RecyclerViewFastScroller;


/**
 * Created by brad on 2017/02/12.
 */

public class ContactAdapter extends CursorRecyclerViewAdapter<ContactAdapter.ContactsViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter {
    String username, hasphone,phone;
    public ContactAdapter(Context context, Cursor cursor, String id) {
        super(context, cursor, id);
    }

    // Define listener member variable
    private static OnRecyclerViewItemClickListener mListener;

    // Define the listener interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(String text,String text2);
    }

    // Define the method that allows the parent activity or fragment to define the listener.
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder viewHolder, Cursor cursor) {
        ContentResolver cr = mContext.getContentResolver();

        String lastnumber = "0";


        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int count=0;
                String number = null;
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]
                            {id}, null);
                    while (pCur.moveToNext()) {
                        number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.e("lastnumber ", lastnumber);
                        Log.e("number", number);
                        count++;

                        if (number.equals(lastnumber)) {

                        } else {
                            lastnumber = number;

                            Log.e("lastnumber ", lastnumber);
                            int type = pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    Log.e("Not Inserted", "Not inserted");
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    viewHolder.setUsername(username);
                                    viewHolder.setPhoneNumber(phone);

                                 //   databaseHandler.insertContact(id, name, lastnumber, 0);
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    Log.e("Not Inserted", "Not inserted");
                                    break;
                            }

                        }

                    }
                    pCur.close();
                }

            }


            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Data
                    .DISPLAY_NAME));

            hasphone = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.HAS_PHONE_NUMBER));
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int totalcontact = cursor.getCount();
            Log.d("total contact list", String.valueOf(totalcontact));

            if (hasphone.equalsIgnoreCase("1")) {
                viewHolder.setUsername(username);
                viewHolder.setPhoneNumber(phone);
            }
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
    }

    @Override
    public String getTextToShowInBubble(final int pos) {

        return Character.toString(username.charAt(0));
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContactUsername,textViewContactUserphone;
        ImageView imageViewContactDisplay;
        LinearLayout contactItemContainer;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            textViewContactUsername = (TextView) itemView.findViewById(R.id
                    .text_view_contact_username);
            textViewContactUserphone = (TextView) itemView.findViewById(R.id
                    .text_view_contact_phone_number);

            imageViewContactDisplay = (ImageView) itemView.findViewById(R.id
                    .image_view_contact_display);

            contactItemContainer = (LinearLayout) itemView.findViewById(R.id
                    .contact_item_container);
            this.contactItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send the text to the listener, i.e Activity.
                    mListener.onItemClicked(textViewContactUserphone.getText().toString(),textViewContactUsername.getText().toString());
                }
            });
        }

        public void setUsername(String username) {
            textViewContactUsername.setText(username);
        }
        public void setPhoneNumber(String phone) {
            textViewContactUserphone.setText(phone);
        }
    }
}
