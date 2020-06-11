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

/**
 * @author Adham Mahmoud
 * @version 1
 * This class is responsible for changing the user's email
 */
public class ChangeUserEmail extends Fragment {

    private Button ChangeEmail;
    private Button GoBack;
    private EditText NewEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_email, container, false);

        ChangeEmail = view.findViewById(R.id.edit_email_confirm_button);
        GoBack = view.findViewById(R.id.edit_email_go_back_button);
        NewEmail = view.findViewById(R.id.edit_email_New_Email_text);

        ChangeEmail.setEnabled(false);

        ChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)requireActivity())
                        .ConfirmEmailChange(NewEmail.getText().toString())){
                    Toast.makeText(requireActivity(), "Email is changed successfully"
                            , Toast.LENGTH_LONG).show();
                    ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                }else{
                    Toast.makeText(getActivity(), "Email changing operation failed"
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

        NewEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailInput = NewEmail.getText().toString().trim();

                ChangeEmail.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailInput).matches());
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
