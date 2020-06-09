package com.example.pacify;

import android.content.Intent;
import android.net.Uri;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;


public class ArtistUploadFragment extends Fragment {
    Button selectFile, uoload;

    Button logoutBtn;
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView userPhoto;

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

        userPhoto = (ImageView) view.findViewById (R.id.userPhoto);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.userPhoto:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                        break;
                };
            }
        });
        return view;
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData ();
            userPhoto.setImageURI(selectedImage);
        }
    }

}
