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
import com.example.pacify.Utilities.Constants;

import java.util.regex.Pattern;

public class ChangeUserPassword  extends Fragment {

    private EditText OldPassword;
    private EditText NewPassword;
    private EditText NewPasswordAgain;
    private Button ChangePassword;
    private Button GoBack;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_password, container, false);

        OldPassword = view.findViewById(R.id.edit_password_old_password_text);
        NewPassword = view.findViewById(R.id.edit_password_new_password_text);
        NewPasswordAgain = view.findViewById(R.id.edit_password_new_password_text_again);
        ChangePassword = view.findViewById(R.id.edit_password_confirm_button);
        GoBack = view.findViewById(R.id.edit_password_go_back_button);

        ChangePassword.setEnabled(false);

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPasswordMatch()){
                    if(((NavigationActivity)getActivity())
                            .ConfirmPasswordChange(OldPassword.getText().toString(),
                                    NewPassword.getText().toString())){
                        Toast.makeText(getActivity(), "Password is changed successfully.", Toast.LENGTH_SHORT).show();
                        GoBack.setText("Done");
                    } else{
                        Toast.makeText(getActivity(), "Password changing failed!", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)getActivity()).OpenSettingsFragment();
            }
        });

        OldPassword.addTextChangedListener(textWatcher);
        NewPasswordAgain.addTextChangedListener(textWatcher);
        NewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String old_password = OldPassword.getText().toString().trim();
                String new_password = NewPassword.getText().toString().trim();
                String new_password_again = NewPasswordAgain.getText().toString().trim();

                ChangePassword.setEnabled(!old_password.isEmpty() &&
                        !new_password.isEmpty() &&
                        !new_password_again.isEmpty() &&
                        CheckPasswordPattern());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!CheckPasswordPattern()){
                    NewPassword.setError("You password should be 8 chars," +
                                         " contain [a-z],[A-Z],[0-9]," +
                                         " and no white spaces allowed");
                }
            }
        });

        return view;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String old_password = OldPassword.getText().toString();
            String new_password = NewPassword.getText().toString();
            String new_password_again = NewPasswordAgain.getText().toString();

            ChangePassword.setEnabled(!old_password.isEmpty() &&
                    !new_password.isEmpty() &&
                    !new_password_again.isEmpty() &&
                    CheckPasswordPattern());
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    private boolean CheckPasswordPattern(){
        return Constants.PASSWORD_PATTERN.matcher(NewPassword
                            .getText().toString()).matches();
    }

    private boolean CheckPasswordMatch(){
        return NewPassword.getText().toString()
                .equals(NewPasswordAgain.getText().toString());
    }

}
