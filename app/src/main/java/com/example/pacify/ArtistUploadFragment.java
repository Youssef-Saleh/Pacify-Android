package com.example.pacify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class ArtistUploadFragment extends Fragment {
    Button selectFile, uoload;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_upload_interface, container, false);



        selectFile = view.findViewById(R.id.chooseSong);
        uoload = view.findViewById(R.id.UploadSong);
        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                   ((ArtistActivity)getActivity()).selectAudio();
               }else
                   ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);

            }
        });
        uoload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ArtistActivity)getActivity()).audioUri != null)
                    ((ArtistActivity)getActivity()).uploadFile ((((ArtistActivity)getActivity()).audioUri));
                else Toast.makeText(getActivity(),"Please Choose a file",Toast.LENGTH_SHORT).show();

            }
        });
        return view;
}


}
