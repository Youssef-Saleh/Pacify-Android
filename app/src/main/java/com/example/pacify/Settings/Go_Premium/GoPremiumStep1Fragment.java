package com.example.pacify.Settings.Go_Premium;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class holds the first step of getting premium subscription,
 * it has a button to take the user to the next step
 */
public class GoPremiumStep1Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_premium_step1, container, false);

        Button getPremiumButton = view.findViewById(R.id.go_premium_step1_button);
        Button backArrow = view.findViewById(R.id.go_premium_step1_go_back_button);

        getPremiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).openGoPremiumStep2Fragment();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
            }
        });

        return view;
    }
}