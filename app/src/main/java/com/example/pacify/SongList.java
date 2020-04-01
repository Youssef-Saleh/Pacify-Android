package com.example.pacify;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class SongList extends AppCompatActivity  {

    ListView songListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_view);
        songListView=(ListView) findViewById(R.id.songListView);
        List<Song> songs=new ArrayList<>();

        for(int i=0 ; i<15;i++){
        Song testSong= new Song("1","STILL TESTIIIING", "2","1");
        songs.add(testSong);}



        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);
    }




}
