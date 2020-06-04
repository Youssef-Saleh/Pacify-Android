package com.example.pacify.Settings.Go_Premium;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.CommonFunctions;
import com.example.pacify.Forget_Password.ForgetPasswordActivity;
import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

public class GoPremiumStep3Fragment extends Fragment {

    private String Vcode;
    private Button buttonConfirm;
    private EditText editTextVerCode;
    private TextView VcodeHint;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_go_premium_step3, container, false);

        buttonConfirm = view.findViewById(R.id.go_premium_step3_conf_button);
        editTextVerCode = view.findViewById(R.id.go_premium_step3_v_code);
        VcodeHint = view.findViewById(R.id.go_premium_step3_ver_code_hint);

        Vcode = GoPremiumStep2Fragment.VCode;

        buttonConfirm.setEnabled(false);
        VcodeHint.setText("Hint: verification code is " + Vcode);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextVerCode.getText().toString().trim().equals(Vcode.trim())) {
                    CommonFunctions.hideKeyboard((NavigationActivity)requireActivity());
                    ((NavigationActivity)requireActivity()).upgradeUserToPremium();
                    ((NavigationActivity)requireActivity()).GoBackFromSettings();
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
