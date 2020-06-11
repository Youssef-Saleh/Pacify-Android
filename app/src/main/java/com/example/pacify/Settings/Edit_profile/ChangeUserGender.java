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
 * This class is responsible for changing the user's gender
 */
public class ChangeUserGender extends Fragment {

    private Button Male_btn;
    private Button Female_btn;
    private Button GoBack;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_user_gender, container, false);

        Male_btn = view.findViewById(R.id.male_button);
        Female_btn = view.findViewById(R.id.female_button);
        GoBack = view.findViewById(R.id.edit_gender_go_back_button);


        Male_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)requireActivity()).ConfirmGenderChange("male")){
                    Toast.makeText(requireActivity(), "Gender changed to Male"
                            , Toast.LENGTH_LONG).show();
                    ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                }else{
                    Toast.makeText(requireActivity(), "Gender changing operation failed"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        Female_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((NavigationActivity)requireActivity()).ConfirmGenderChange("female")){
                    Toast.makeText(requireActivity(), "Gender changed to Female"
                            , Toast.LENGTH_LONG).show();
                    ((NavigationActivity)requireActivity()).OpenSettingsFragment();
                }else{
                    Toast.makeText(requireActivity(), "Gender changing operation failed"
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

        return view;
    }
}
