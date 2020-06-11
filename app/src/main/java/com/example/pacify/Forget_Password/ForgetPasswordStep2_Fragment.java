package com.example.pacify.Forget_Password;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacify.R;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class is responsible to let the user enter the verification
 * code sent to him by email
 */
public class ForgetPasswordStep2_Fragment extends Fragment {

    private String Vcode;
    private Button buttonConfirm;
    private EditText editTextVerCode;
    private TextView VcodeHint;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forget_password_step2, container, false);

        buttonConfirm = view.findViewById(R.id.ForgetPass_step2_conf_button);
        editTextVerCode = view.findViewById(R.id.ForgetPass_step2_v_code);
        VcodeHint = view.findViewById(R.id.ForgetPass_step2_ver_code_hint);

        Vcode = ((ForgetPasswordActivity)requireActivity()).VerCode;

        buttonConfirm.setEnabled(false);
        VcodeHint.setText("Hint: verification code is " + Vcode);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextVerCode.getText().toString().trim().equals(Vcode.trim())) {
                    ((ForgetPasswordActivity)requireActivity()).openForgetPasswordStep3Fragment();
                }
                else{
                    editTextVerCode.setTextColor(Color.RED);
                }
            }
        });

        editTextVerCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String verificationCode_text = editTextVerCode.getText().toString().trim();

                editTextVerCode.setTextColor(Color.WHITE);

                buttonConfirm.setEnabled(verificationCode_text.length() == Vcode.length());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    return view;
    }
}
