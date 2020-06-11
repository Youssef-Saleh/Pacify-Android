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

/**
 * @author Adham Mahmoud
 * @version 1
 * This class is responsible for changing the user's password
 */
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
                    if(((NavigationActivity)requireActivity())
                            .ConfirmPasswordChange(OldPassword.getText().toString(),
                                    NewPassword.getText().toString())){
                        Toast.makeText(requireActivity(), "Password is changed successfully."
                                , Toast.LENGTH_SHORT).show();
                        ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                    } else{
                        Toast.makeText(requireActivity(), "Password changing failed!"
                                , Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(requireActivity(), "Passwords do not match."
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
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
                        !new_password_again.isEmpty() &&
                        CheckPasswordPattern());

                if(new_password.length() > 16) {
                    NewPassword.setError("Password is too long (max.16)");
                }else if(!CheckPasswordPattern()){
                    NewPassword.setError("You password should be 8 chars," +
                            " contain [a-z],[A-Z],[0-9]," +
                            " and no white spaces allowed");
                }else{
                    NewPasswordAgain.setError(null);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String old_password = OldPassword.getText().toString();
            String new_password_again = NewPasswordAgain.getText().toString();

            ChangePassword.setEnabled(!old_password.isEmpty() &&
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
