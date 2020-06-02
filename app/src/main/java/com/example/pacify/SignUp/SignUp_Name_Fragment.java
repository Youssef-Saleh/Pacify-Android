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


public class SignUp_Name_Fragment extends Fragment {

    private Button buttonCreate;
    private Button buttonBack;
    private EditText editTextName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_name, container, false);

        buttonCreate = view.findViewById(R.id.signUp_Name_Create_button);
        buttonBack = view.findViewById(R.id.signUp_Name_Back_button);
        editTextName = view.findViewById(R.id.signUp_Name_editText);

        buttonCreate.setEnabled(false); //The button is initially disabled

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //The button is disabled if the input email is not valid
                String nameInput = editTextName.getText().toString().trim();
                buttonCreate.setEnabled(!nameInput.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)requireActivity()).signUp_name =
                        editTextName.getText().toString().trim();
                ((SignUpActivity)requireActivity()).openSignUpVerificationFragment();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)requireActivity()).openSignUpGenderFragment();
            }
        });

        return view;
    }
}


