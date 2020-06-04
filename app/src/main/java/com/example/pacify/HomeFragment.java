package com.example.pacify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ImageButton btnPop;
    ImageView artist;
    TextView artistName;
    ImageButton adele;
    @SuppressLint("ResourceType")
    public void showSongList(View v){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        btnPop=v.findViewById(R.id.imageButton9);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("key","Emeniem");
                Fragment fragment= new artistview();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();

            }
        });
        adele = v.findViewById((R.id.imageButton5));

        adele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key","Adele");
                Fragment fragment= new artistview();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();



            }
        });
        return v;
    }
}
