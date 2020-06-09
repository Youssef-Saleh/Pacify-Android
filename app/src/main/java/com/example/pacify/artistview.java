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
import android.widget.Toast;

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

<<<<<<< HEAD
        }else if(name =="Eminem"){
=======
        }else if(name.equals("Emeniem")){
>>>>>>> 86eba2a72143b5951c251b9f529396ce73e85645
            artistpic.setImageResource(R.drawable.emspotify);
        }else if (name == "Sia"){
            artistpic.setImageResource(R.drawable.sia);
        }else if (name == "Martin Garrix"){
            artistpic.setImageResource(R.drawable.martingarix);
        }else if (name == "Alan Walker"){
            artistpic.setImageResource(R.drawable.alanwalker);
        }else if (name == "Ed Sheeran"){
            artistpic.setImageResource((R.drawable.ed));
        }else if (name == "Marshmello"){
            artistpic.setImageResource(R.drawable.marshmello);
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
                    Toast.makeText(getActivity(), "You have unfollowed "+name, Toast.LENGTH_SHORT).show();

                }else {
                    ifFollowed = true;
                    Toast.makeText(getActivity(), "You have followed "+name, Toast.LENGTH_SHORT).show();
                    showIfFollowed(true,v);
                }
                if (name == "Adele"){
                    ((NavigationActivity)getActivity()).artistFive.setIsFollowed(ifFollowed);

                }else if(name =="Eminem"){
                    ((NavigationActivity)getActivity()).artistThree.setIsFollowed(ifFollowed);
                }else if (name == "Sia"){
                    ((NavigationActivity)getActivity()).artistOne.setIsFollowed(ifFollowed);
                }else if (name == "Martin Garrix"){
                    ((NavigationActivity)getActivity()).artistTwo.setIsFollowed(ifFollowed);
                }else if (name == "Alan Walker"){
                    ((NavigationActivity)getActivity()).artistFour.setIsFollowed(ifFollowed);
                }else if (name == "Ed Sheeran"){
                    ((NavigationActivity)getActivity()).artistSix.setIsFollowed(ifFollowed);
                }else if (name == "Marshmello"){
                    ((NavigationActivity)getActivity()).artistSeven.setIsFollowed(ifFollowed);
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
