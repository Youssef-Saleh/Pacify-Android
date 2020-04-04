package com.example.pacify.Settings;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.facebook.AccessToken;

public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.GRAY);
        return view;
    }

    private AccessToken accessToken;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_settings, rootKey);


        Preference GoBack = findPreference("go_back");
        GoBack.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).GoBackFromSettings();
                return true;
            }
        });

        Preference EditAccountPreference = findPreference("edit_profile");
        EditAccountPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).GoToEditProfile();
                return true;
            }
        });

        Preference logoutPreference = findPreference("logout");
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            logoutPreference.setSummary("You are logged by Facebook\n"
                    + "As " + ((NavigationActivity)getActivity()).UserName);
        }else{
            logoutPreference.setSummary("You are logged in as "
                    + ((NavigationActivity)getActivity()).UserName);
        }
        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)getActivity()).LogOut();
                return true;
            }
        });

    }
}