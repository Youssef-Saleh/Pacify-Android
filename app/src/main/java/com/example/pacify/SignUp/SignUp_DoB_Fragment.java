package com.example.pacify.SignUp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

public class SignUp_DoB_Fragment extends Fragment {

    private DatePicker MyDoB;
    private Button ConfirmDoB;
    private Button GoBack;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_dob, container, false);

        MyDoB = view.findViewById(R.id.signUp_datePicker);
        GoBack = view.findViewById(R.id.signUp_dob_Back_button);
        ConfirmDoB = view.findViewById(R.id.signUp_dob_Next_button);

        ConfirmDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)getActivity()).signUp_dob_year =  MyDoB.getYear();
                ((SignUpActivity)getActivity()).signUp_dob_month = MyDoB.getMonth() + 1;
                ((SignUpActivity)getActivity()).signUp_dob_day = MyDoB.getDayOfMonth();
                ((SignUpActivity)getActivity()).openSignUpGenderFragment();
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignUpActivity)getActivity()).openSignUpPasswordFragment();
            }
        });

        return view;
    }

}
