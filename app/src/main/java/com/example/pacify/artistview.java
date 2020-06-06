package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class artistview extends Fragment {



String name ;
TextView artistname;
ImageView artistpic;
    ListView songListView;
    List<Song> songs=new ArrayList<>();
    int currentIndex=0;
    public List<Song> getSongList(){
        return songs;
    }

    public void setSongList(List<Song> songsToSet){
        songs=songsToSet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v= inflater.inflate(R.layout.artist_view, container, false);
        Bundle bundle = this.getArguments();
        songListView=(ListView) v.findViewById(R.id.artistSongs);
        if(bundle != null){
            name=bundle.getString("key");
        }

        artistname = v.findViewById(R.id.artistName);
        artistpic = v.findViewById(R.id.artistPhoto);
        if (name == "Adele"){
            artistpic.setImageResource(R.drawable.adele);
        }else if(name =="Emeniem"){
            artistpic.setImageResource(R.drawable.emspotify);
        }
        artistname.setText(name);

        songs=((NavigationActivity)getActivity()).songs;
        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);


        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=songs.get(position);
                String songAdress=song.getUrl();
                String songName = song.getTitle();
                ((NavigationActivity)getActivity()).currentSongIndex=position;
                ((NavigationActivity)getActivity()).songs=songs;
                ((NavigationActivity)getActivity()).startStreamingService(songAdress);
                // ((NavigationActivity)getActivity()).setSongNameNav(songName);
            }
        });
        return v;
    }


}
