package com.example.pacify.Settings.Go_Premium;

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

import com.example.pacify.CommonFunctions;
import com.example.pacify.Forget_Password.ForgetPasswordActivity;
import com.example.pacify.NavigationActivity;
import com.example.pacify.R;


public class GoPremiumStep2Fragment extends Fragment {

    public static String VCode;
    private Button buttonGetCode;
    private EditText editTextEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_go_premium_step2, container, false);

        buttonGetCode = view.findViewById(R.id.go_premium_step2_get_code_button);
        editTextEmail = view.findViewById(R.id.go_premium_step2_email_text);

        buttonGetCode.setEnabled(false);

        buttonGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VCode = CommonFunctions.GenerateRandChars(8);
                ((NavigationActivity)requireActivity()).SendEmailRequest();
                ((NavigationActivity)requireActivity()).openGoPremiumStep3Fragment();
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

                buttonGetCode.setEnabled(Patterns.EMAIL_ADDRESS
                        .matcher(emailOrUsername_text)
                        .matches());
            }
        });

        return view;
    }
}
