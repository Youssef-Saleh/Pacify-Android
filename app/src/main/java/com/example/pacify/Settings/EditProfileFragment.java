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

/**
 * @author Adham Mahmoud
 * @version 1
 * This class (Preference) is responsible to view edit profile objects
 */
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
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                return true;
            }
        });

        Preference ChangeEmail = findPreference("edit_email");
        ChangeEmail.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditEmail();
                return true;
            }
        });

        Preference ChangePassword = findPreference("edit_password");
        ChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditPassword();
                return true;
            }
        });

        Preference EditPhoneNumber = findPreference("edit_phone");
        EditPhoneNumber.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditPhoneNumber();
                return true;
            }
        });

        Preference EditCounty = findPreference("edit_country");
        EditCounty.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditCounty();
                return true;
            }
        });

        Preference EditGender = findPreference("edit_gender");
        EditGender.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditGender();
                return true;
            }
        });

        Preference EditDoB = findPreference("edit_dob");
        EditDoB.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                ((NavigationActivity)requireActivity()).GoToEditDoB();
                return true;
            }
        });

    }

}
