package com.example.pacify;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;


public class SearchFragment extends Fragment {
    ImageButton btnPop;
    Button btnCountry;
    Button btnRock;
    Button btnHipHop;
    Button btnArabic;
    Button btnParty;
    Button btnCharts;

    List<Song> songs = new ArrayList<>();
    ListView songsListView;

    public void showSongList(){

//        Intent intent= new Intent(getContext(),SongList.class);
//        startActivity(intent);
        Fragment fragment= new SongList();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();


    }
    public void popGenre(View view){

        showSongList();

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

    public void chartsGenre(View view){
        Toast.makeText(getActivity(), "Charts", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        View w= inflater.inflate(R.layout.song_list_view, container, false);
        songsListView= (ListView) w.findViewById(R.id.songListView);
        btnPop=v.findViewById(R.id.btnPop);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                popGenre(w);
            }
        });
        btnCountry=v.findViewById(R.id.btnCountry);
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryGenre(v);
            }
        });
        btnRock=v.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rockGenre(v);
            }
        });
        btnHipHop=v.findViewById(R.id.btnHipHop);
        btnHipHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiphopGenre(v);
            }
        });
        btnArabic=v.findViewById(R.id.btnArabic);
        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arabicGenre(v);
            }
        });
        btnParty=v.findViewById(R.id.btnParty);
        btnParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyGenre(v);
            }
        });
        btnCharts=v.findViewById(R.id.btnCharts);
        btnCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartsGenre(v);
            }
        });


        return v;
    }


}
