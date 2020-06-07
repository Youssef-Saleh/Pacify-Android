package com.example.pacify.SignUp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.R;
import com.example.pacify.Utilities.Constants;


public class SignUp_Gender_Fragment extends Fragment {

    private Button Male_btn;
    private Button Female_btn;
    private Button buttonBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_gender, container, false);

        Male_btn = view.findViewById(R.id.male_button);
        Female_btn = view.findViewById(R.id.female_button);
        buttonBack = view.findViewById(R.id.signUp_gender_Back_button);

        Male_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)requireActivity()).signUp_gender = "male";
                ((SignUpActivity)requireActivity()).openSignUpNameFragment();
            }
        });

        Female_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)requireActivity()).signUp_gender = "female";
                ((SignUpActivity)requireActivity()).openSignUpNameFragment();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)requireActivity()).openSignUpDobFragment();
            }
        });

        return view;
    }

}


