package com.example.pacify;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.pacify.Utilities.PreferenceUtilities;

public class MySettingsFragment extends PreferenceFragmentCompat {



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey);


        Preference EditAccountPreference = findPreference("edit_profile");
        EditAccountPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                return true;
            }
        });

        Preference logoutPreference = findPreference("logout");
        String name = PreferenceUtilities.getUserName(getContext());
        logoutPreference.setSummary("You are logged in as " + name);
        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).LogOut();
                return true;
            }
        });

        Preference GoBack = findPreference("go_back");
        GoBack.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).GoBackFromSettings();
                return true;
            }
        });
    }
}