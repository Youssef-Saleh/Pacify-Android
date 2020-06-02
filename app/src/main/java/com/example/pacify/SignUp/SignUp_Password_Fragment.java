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


public class SignUp_Password_Fragment extends Fragment {

    private Button buttonNext;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signup_password, container, false);

        buttonNext = view.findViewById(R.id.signUp_password_Next_button);
        editTextPassword = view.findViewById(R.id.signUp_Password_editText);
        editTextConfirmPassword = view.findViewById(R.id.signUp_Password_confirm_editText);
        buttonBack = view.findViewById(R.id.signUp_password_Back_button);

        buttonNext.setEnabled(false); //The button is initially disabled

        editTextPassword.addTextChangedListener(PasswordTextWatcher);

        editTextConfirmPassword.addTextChangedListener(PasswordTextWatcher);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPasswordMatch()) {
                    ((SignUpActivity)requireActivity()).signUp_password =
                            editTextPassword.getText().toString();
                    ((SignUpActivity)requireActivity()).openSignUpDobFragment();
                }else {
                    Toast.makeText(getActivity(), "Passwords do not match."
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ((SignUpActivity)requireActivity()).openSignUpEmailFragment();
            }
        });

        return view;
    }

    private TextWatcher PasswordTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
        @Override
        public void afterTextChanged(Editable s) {
            String new_password = editTextPassword.getText().toString();
            String new_password_again = editTextConfirmPassword.getText().toString();

            if(new_password.length() > 16) {
                editTextConfirmPassword.setError("Password is too long (max.16)");
            }else if(!CheckPasswordPattern()){
                editTextConfirmPassword.setError("You password should be 8 chars," +
                        " contain [a-z],[A-Z],[0-9]," +
                        " and no white spaces allowed");
            }else{
                editTextConfirmPassword.setError(null);
            }

            buttonNext.setEnabled(!new_password.isEmpty() &&
                    !new_password_again.isEmpty() &&
                    new_password.length() <= 16 &&
                    CheckPasswordPattern());
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    };

    private boolean CheckPasswordPattern(){
        return Constants.PASSWORD_PATTERN.matcher(editTextPassword
                .getText().toString()).matches();
    }

    private boolean CheckPasswordMatch(){
        return editTextPassword.getText().toString()
                .equals(editTextConfirmPassword.getText().toString());
    }
}