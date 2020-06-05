package com.example.pacify.Settings.Edit_profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

public class ChangeUserDoB extends Fragment {

    private DatePicker MyDoB;
    private Button ConfirmDoB;
    private Button GoBack;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_dob, container, false);

        MyDoB = view.findViewById(R.id.datePicker);
        GoBack = view.findViewById(R.id.edit_dob_go_back_button);
        ConfirmDoB = view.findViewById(R.id.edit_Dob_confirm_button);

        ConfirmDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)requireActivity()).ConfirmDobChange(MyDoB.getYear()
                        ,MyDoB.getMonth() + 1, MyDoB.getDayOfMonth())){
                    Toast.makeText(getActivity(), "Date of birth is changed successfully,", Toast.LENGTH_LONG).show();
                    GoBack.setText("Done");
                }else{
                    Toast.makeText(getActivity(), "Date of birth changing operation failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
            }
        });

        return view;
    }
}
