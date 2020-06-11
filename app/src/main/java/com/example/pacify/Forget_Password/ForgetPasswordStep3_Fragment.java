package com.example.pacify.Forget_Password;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pacify.R;
import com.example.pacify.Utilities.Constants;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class is responsible to let the user enter the new password
 * for his account with password validation
 */
public class ForgetPasswordStep3_Fragment extends Fragment {

    private EditText NewPassword;
    private EditText NewPasswordAgain;
    private Button ChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forget_password_step3, container, false);

        NewPassword = view.findViewById(R.id.ForgetPass_step3_new_password_text);
        NewPasswordAgain = view.findViewById(R.id.ForgetPass_step3_new_password_text_again);
        ChangePassword = view.findViewById(R.id.ForgetPass_step3_recover_password_confirm_button);

        ChangePassword.setEnabled(false);

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPasswordMatch()){
                    ((ForgetPasswordActivity)requireActivity())
                            .RecoverPasswordRequest( NewPassword.getText().toString().trim());

                    Toast.makeText(getActivity(), "Password is changed successfully."
                            , Toast.LENGTH_LONG).show();

                    ((ForgetPasswordActivity)requireActivity()).openLoginActivity();
                } else{
                    Toast.makeText(getActivity(), "Passwords do not match."
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        NewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String new_password = NewPassword.getText().toString().trim();
                String new_password_again = NewPasswordAgain.getText().toString().trim();

                ChangePassword.setEnabled(!new_password_again.isEmpty() &&
                        CheckPasswordPattern());

                if(new_password.length() > 16) {
                    NewPasswordAgain.setError("Password is too long (max.16)");
                }else if(!CheckPasswordPattern()){
                    NewPasswordAgain.setError("You password should be 8 chars," +
                            " contain [a-z],[A-Z],[0-9]," +
                            " and no white spaces allowed");
                }else{
                    NewPasswordAgain.setError(null);
                }
            }
        });

        NewPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String new_password_again = NewPasswordAgain.getText().toString();

                ChangePassword.setEnabled(!new_password_again.isEmpty() &&
                        CheckPasswordPattern());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    private boolean CheckPasswordPattern(){
        return Constants.PASSWORD_PATTERN.matcher(NewPassword
                .getText().toString()).matches();
    }

    private boolean CheckPasswordMatch(){
        return NewPassword.getText().toString()
                .equals(NewPasswordAgain.getText().toString());
    }
}