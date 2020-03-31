package com.example.pacify.Settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

public class EditProfileFragment extends PreferenceFragmentCompat {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.GRAY);
        return view;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_edit_profile, rootKey);

        Preference GoBackToSettings = findPreference("go_back_to_settings");
        GoBackToSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).OpenSettingsFragment();
                return true;
            }
        });

        Preference ChangeEmail = findPreference("edit_email");
        ChangeEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).GoToEditEmail();
                return true;
            }
        });

        Preference ChangePassword = findPreference("edit_password");
        ChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).GoToEditPassword();
                return true;
            }
        });

        Preference EditPersonalData = findPreference("edit_personal_data");
        EditPersonalData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return true;
            }
        });
    }

}
