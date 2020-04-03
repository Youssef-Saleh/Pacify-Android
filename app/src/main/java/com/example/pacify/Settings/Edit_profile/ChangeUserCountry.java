package com.example.pacify.Settings.Edit_profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

public class ChangeUserCountry extends Fragment {

    private Button ChangeCounty;
    private Button GoBack;
    private EditText NewCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_country, container, false);

        ChangeCounty = view.findViewById(R.id.edit_country_confirm_button);
        GoBack = view.findViewById(R.id.edit_country_go_back_button);
        NewCountry = view.findViewById(R.id.edit_country_name_text);

        ChangeCounty.setEnabled(false);

        ChangeCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)getActivity()).ConfirmCountryChange(NewCountry.getText().toString())){
                    Toast.makeText(getActivity(), "Country is changed successfully", Toast.LENGTH_LONG).show();
                    GoBack.setText("Done");
                }else{
                    Toast.makeText(getActivity(), "Country changing operation failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)getActivity()).OpenSettingsFragment();
            }
        });

        NewCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String countryInput = NewCountry.getText().toString().trim();

                ChangeCounty.setEnabled(!countryInput.isEmpty());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }
}
