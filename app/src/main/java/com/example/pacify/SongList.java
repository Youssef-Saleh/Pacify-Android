package com.example.pacify;

import android.widget.AdapterView;
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
    List<Song> songs=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.song_list_view, container, false);

        songListView=(ListView) v.findViewById(R.id.songListView);




        Song testSong= new Song("1","Dancin","https://www.mboxdrive.com/dancin%202.mp3", "0","0");
        songs.add(testSong);
        testSong= new Song("2","katyusha","http://www.noiseaddicts.com/samples_1w72b820/1450.mp3", "0","0");
        songs.add(testSong);
        testSong= new Song("3","Real national anthem","http://www.noiseaddicts.com/samples_1w72b820/4250.mp3", "0","0");
        songs.add(testSong);
        testSong= new Song("4","Egyptian national anthem","http://www.noiseaddicts.com/samples_1w72b820/4051.mp3", "0","0");
        songs.add(testSong);
        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=songs.get(position);
                String songAdress=song.getUrl();
                ((NavigationActivity)getActivity()).startStreamingService(songAdress);
            }
        });
        return v;
    }
}
