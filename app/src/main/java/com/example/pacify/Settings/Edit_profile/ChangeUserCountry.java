package com.example.pacify.Settings.Edit_profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.Constants;
import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

import java.util.Objects;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class is responsible for changing the user's country
 */
public class ChangeUserCountry extends Fragment {

    private Button ChangeCounty;
    private Button GoBack;
    private AutoCompleteTextView NewCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_country, container, false);

        ChangeCounty = view.findViewById(R.id.edit_country_confirm_button);
        GoBack = view.findViewById(R.id.edit_country_go_back_button);
        NewCountry = view.findViewById(R.id.edit_country_name_text);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(requireActivity(),
                        R.layout.fragment_change_user_country, Constants.countries);
        //NewCountry.setAdapter(adapter);


        ChangeCounty.setEnabled(false);

        ChangeCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)requireActivity())
                        .ConfirmCountryChange(NewCountry.getText().toString())){
                    Toast.makeText(requireActivity(), "Country is changed successfully"
                            , Toast.LENGTH_LONG).show();
                    ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                }else{
                    Toast.makeText(requireActivity(), "Country changing operation failed"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
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
