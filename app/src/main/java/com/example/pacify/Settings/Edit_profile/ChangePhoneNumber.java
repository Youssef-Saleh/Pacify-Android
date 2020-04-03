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

public class ChangePhoneNumber extends Fragment {

    private Button ChangePhone;
    private Button GoBack;
    private EditText NewNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_phone_number, container, false);

        ChangePhone = view.findViewById(R.id.edit_phone_number_confirm_button);
        GoBack = view.findViewById(R.id.edit_phone_number_go_back_button);
        NewNumber = view.findViewById(R.id.edit_phone_number_text);

        ChangePhone.setEnabled(false);

        ChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)getActivity()).ConfirmPhoneChange(NewNumber.getText().toString())){
                    Toast.makeText(getActivity(), "Phone number is changed successfully", Toast.LENGTH_LONG).show();
                    GoBack.setText("Done");
                }else{
                    Toast.makeText(getActivity(), "Phone number changing operation failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)getActivity()).OpenSettingsFragment();
            }
        });

        NewNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneInput = NewNumber.getText().toString().trim();

                ChangePhone.setEnabled(Patterns.PHONE.matcher(phoneInput).matches());
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
