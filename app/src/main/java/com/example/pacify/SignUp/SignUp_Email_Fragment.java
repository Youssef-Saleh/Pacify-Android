package com.example.pacify.SignUp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;


public class SignUp_Email_Fragment extends Fragment {

    private Button buttonNext;
    private EditText editTextEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_email, container, false);

        buttonNext = view.findViewById(R.id.signUp_email_Next_button);
        editTextEmail = view.findViewById(R.id.signUp_Email_editText);

        buttonNext.setEnabled(false); //The button is initially disabled

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //The button is disabled if the input email is not valid
                String emailInput = editTextEmail.getText().toString().trim();
                buttonNext.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailInput).matches());
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)getActivity()).signUp_email = editTextEmail.getText()
                        .toString().trim();

                ((SignUpActivity)getActivity()).hideKeyboard(getActivity());
                ((SignUpActivity)getActivity()).openSignUpPasswordFragment();
            }
        });

        return view;
    }
}


