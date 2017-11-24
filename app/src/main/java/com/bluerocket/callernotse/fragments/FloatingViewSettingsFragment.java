package com.bluerocket.callernotse.fragments;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;

import com.bluerocket.callernotse.R;

public class FloatingViewSettingsFragment extends PreferenceFragment {

    /**
     * FloatingViewSettingsFragmentを生成します。
     *
     * @return FloatingViewSettingsFragment
     */
    public static FloatingViewSettingsFragment newInstance() {
        final FloatingViewSettingsFragment fragment = new FloatingViewSettingsFragment();
        return fragment;
    }

    /**
     * コンストラクタ
     */
    public FloatingViewSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_floatingview, null);
    }
}
