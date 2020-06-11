package com.example.pacify.Forget_Password;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.pacify.R;


public class ForgetPasswordStep1_Fragment extends Fragment {

    private Button buttonGetLink;
    private EditText editTextEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forget_password_step1, container, false);

        buttonGetLink = view.findViewById(R.id.GetLink_button);
        editTextEmail = view.findViewById(R.id.Email_ForgetPass_text);

        buttonGetLink.setEnabled(false);

        buttonGetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ForgetPasswordActivity)requireActivity()).GenerateRandChars_ForgetPass(6);
                ((ForgetPasswordActivity)requireActivity())
                        .SendEmailRequest(editTextEmail.getText().toString().trim());
                ((ForgetPasswordActivity)requireActivity()).openForgetPasswordStep2Fragment();
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {
                String emailOrUsername_text = editTextEmail.getText().toString().trim();

                buttonGetLink.setEnabled(Patterns.EMAIL_ADDRESS
                        .matcher(emailOrUsername_text)
                        .matches());
            }
        });

        return view;
    }
}
