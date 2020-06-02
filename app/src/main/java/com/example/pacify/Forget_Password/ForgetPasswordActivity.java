package com.example.pacify.Forget_Password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pacify.CommonFunctions;
import com.example.pacify.LoginActivity;
import com.example.pacify.R;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {

    public String VerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        openForgetPasswordStep1Fragment();
    }

    public void GenerateRandChars_ForgetPass(int size){
        VerCode = CommonFunctions.GenerateRandChars(size);
    }

    public void SendEmailRequest(){
        //TODO(Adham): Send email with verification code to recover password request
    }

    public void RecoverPasswordRequest(){
        //TODO(Adham): send a change password request
    }

    public void openLoginActivity(){
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openForgetPasswordStep1Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep1_Fragment())
                .commit();
    }

    public void openForgetPasswordStep2Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep2_Fragment())
                .commit();
    }

    public void openForgetPasswordStep3Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep3_Fragment())
                .commit();
    }
}
