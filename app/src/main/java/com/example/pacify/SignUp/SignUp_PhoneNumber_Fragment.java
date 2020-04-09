package com.example.pacify.SignUp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.R;
import com.example.pacify.Utilities.Constants;


public class SignUp_PhoneNumber_Fragment extends Fragment {

    private Button buttonNext;
    private EditText editTextPhone;
    private Button buttonBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_phone_number
                , container, false);

        buttonNext = view.findViewById(R.id.signup_phone_number_confirm_button);
        editTextPhone = view.findViewById(R.id.signup_phone_number_text);
        buttonBack = view.findViewById(R.id.signup_phone_number_go_back_button);

        buttonNext.setEnabled(false); //The button is initially disabled

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonNext.setEnabled(!editTextPhone.getText().toString().trim().isEmpty());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)getActivity()).signUp_phone_num = editTextPhone.getText()
                        .toString().trim();
                ((SignUpActivity)getActivity()).hideKeyboard(getActivity());
                ((SignUpActivity)getActivity()).openSignUpGenderFragment();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)getActivity()).openSignUpDobFragment();
            }
        });

        return view;
    }
}