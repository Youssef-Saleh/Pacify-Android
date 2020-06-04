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

public class GoPremiumStep1Fragment extends Fragment {

    private Button getPremButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_premium_step1, container, false);

        getPremButton = view.findViewById(R.id.go_premium_step1_button);

        getPremButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).openGoPremiumStep2Fragment();
            }
        });

        return view;
    }
}