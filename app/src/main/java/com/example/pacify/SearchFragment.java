package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


public class SearchFragment extends Fragment {
    Button btnPop;
    Button btnCountry;
    Button btnRock;
    Button btnHipHop;
    Button btnArabic;
    Button btnParty;

    public void popGenre(View view){
        Toast.makeText(getActivity(), "Pop", Toast.LENGTH_LONG).show();
    }
    public void countryGenre(View view){
        Toast.makeText(getActivity(), "Country", Toast.LENGTH_LONG).show();
    }

    public void rockGenre(View view){
        Toast.makeText(getActivity(), "Rock", Toast.LENGTH_LONG).show();
    }

    public void hiphopGenre(View view){
        Toast.makeText(getActivity(), "Hip-Hop", Toast.LENGTH_LONG).show();
    }

    public void arabicGenre(View view){
        Toast.makeText(getActivity(), "Arabic", Toast.LENGTH_LONG).show();
    }

    public void partyGenre(View view){
        Toast.makeText(getActivity(), "Party", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        btnPop=(Button)v.findViewById(R.id.btnPop);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popGenre(v);
            }
        });
        btnCountry=(Button)v.findViewById(R.id.btnCountry);
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryGenre(v);
            }
        });
        btnRock=(Button)v.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rockGenre(v);
            }
        });
        btnHipHop=(Button)v.findViewById(R.id.btnHipHop);
        btnHipHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiphopGenre(v);
            }
        });
        btnArabic=(Button)v.findViewById(R.id.btnArabic);
        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arabicGenre(v);
            }
        });
        btnParty=(Button)v.findViewById(R.id.btnParty);
        btnParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyGenre(v);
            }
        });

        return v;
    }


}
