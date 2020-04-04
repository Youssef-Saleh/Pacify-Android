package com.example.pacify;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class SongList extends Fragment {

    ListView songListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.song_list_view, container, false);

        songListView=(ListView) v.findViewById(R.id.songListView);

        List<Song> songs=new ArrayList<>();

        for(int i=0 ; i<15;i++){
        Song testSong= new Song("1","STILL TESTIIIING", "2","1");
        songs.add(testSong);}

        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);

        return v;
    }
}
