package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class artistview extends Fragment {

    Boolean ifFollowed ;

    String name ;
    TextView artistname;
    ImageView artistpic;
    ListView songListView;
    Button follow;
    List<Song> songs=new ArrayList<>();
    int currentIndex=0;
    public List<Song> getSongList(){
        return songs;
    }

    public void setSongList(List<Song> songsToSet){
        songs=songsToSet;
    }
    public void showIfFollowed(Boolean isFollowed, View view){
            follow = view.findViewById(R.id.followArtist);
            if (isFollowed){
                follow.setText("UnFollow");

            }else {
                follow.setText("Follow");
            }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v= inflater.inflate(R.layout.artist_view, container, false);
        Bundle bundle = this.getArguments();
        songListView=(ListView) v.findViewById(R.id.artistSongs);
        if(bundle != null){
            name=bundle.getString("key");
            ifFollowed = bundle.getBoolean("isFollowed");

        }
        showIfFollowed(ifFollowed,v);
        artistname = v.findViewById(R.id.artistName);
        artistpic = v.findViewById(R.id.artistPhoto);
        if (name.equals("Adele")){
            artistpic.setImageResource(R.drawable.adele);

        }else if(name.equals("Emeniem")){
            artistpic.setImageResource(R.drawable.emspotify);
        }
        artistname.setText(name);

        songs=((NavigationActivity)requireActivity()).songsToShow;
        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifFollowed){
                    ifFollowed = false;
                    showIfFollowed(false,v);

                }else {
                    ifFollowed = true;
                    showIfFollowed(true,v);
                }
                if (name.equals("Adele")){
                    ((NavigationActivity)requireActivity()).artistFive.setIsFollowed(ifFollowed);

                }else if(name.equals("Emeniem")){
                    ((NavigationActivity)requireActivity()).artistThree.setIsFollowed(ifFollowed);
                }
            }
        });
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=songs.get(position);
                String songAdress=song.getUrl();
                String songName = song.getTitle();
                if (song.inQueue){
                    ((NavigationActivity) requireActivity()).currentSongIndex = song.numberInQueue;
                    ((NavigationActivity) requireActivity()).startStreamingService(songAdress);

                }
                else {
                    if (((NavigationActivity) requireActivity()).songQueue.size() > 0) {
                        ((NavigationActivity) requireActivity()).currentSongIndex =
                                ((NavigationActivity) requireActivity()).songQueue.size();
                        song.numberInQueue = ((NavigationActivity) requireActivity()).currentSongIndex;
                    }
                    ((NavigationActivity) requireActivity()).songQueue.add(song);
                    song.inQueue = true;
                    ((NavigationActivity) requireActivity()).startStreamingService(songAdress);
                    // ((NavigationActivity)requireActivity()).setSongNameNav(songName);
                }
            }
        });
        return v;
    }


}
