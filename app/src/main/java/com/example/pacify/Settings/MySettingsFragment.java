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
import com.example.pacify.Utilities.Constants;
import com.facebook.AccessToken;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class (Preference) is responsible to view the objects in the
 * setting main menu
 */
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


        Preference goBack = findPreference("go_back");
        goBack.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoBackFromSettings();
                return true;
            }
        });

        Preference goPremium = findPreference("go_premium");
        goPremium.setVisible(Constants.USER_TYPE.equals("free"));
        goPremium.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).openGoPremiumStep1Fragment();
                return true;
            }
        });

        Preference editAccountPreference = findPreference("edit_profile");
        editAccountPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditProfile();
                return true;
            }
        });

        Preference logoutPreference = findPreference("logout");
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            logoutPreference.setSummary("You are logged using Facebook\n");
        }else{
            logoutPreference.setSummary("You are logged using email");
        }
        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).LogOut();
                return true;
            }
        });

    }
}