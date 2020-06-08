package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ArtistUploadFragment extends Fragment {

    Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_upload_interface, container, false);

        logoutBtn = view.findViewById(R.id.logoutButton);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ArtistActivity)requireActivity()).Logout();
            }
        });

        return view;
    }
}
