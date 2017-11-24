package com.bluerocket.callernotse.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.fragments.DeleteActionFragment;

/**
 * 通知から起動後に削除アクションを行う画面です。
 */
public class DeleteActionActivity extends Activity {

    /**
     * 設定フラグメントのタグ
     */
    private static final String FRAGMENT_TAG_DELETE_ACTION = "delete_action";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_action);

        if (savedInstanceState == null) {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, DeleteActionFragment.newInstance(), FRAGMENT_TAG_DELETE_ACTION);
            ft.commit();
        }

    }
}