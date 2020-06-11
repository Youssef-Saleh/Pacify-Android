package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is Used to make a list of song objects to play
 */
public class SongList extends Fragment {

    ListView songListView;
    Button btnPlayAll;
    List<Song> songs=new ArrayList<>();
    int currentIndex=0;

    public SongList(List<Song> songList){
        songs=songList;
    }
    public List<Song> getSongList(){
        return songs;
    }

    public void setSongList(List<Song> songsToSet){
        songs=songsToSet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v= inflater.inflate(R.layout.song_list_view, container, false);

        songListView=(ListView) v.findViewById(R.id.songListView);
        btnPlayAll=(Button) v.findViewById(R.id.btnPlayAll);
        /**
         * This method is called when th user presses play all button
         * calls playAll function in NavigationActivity
         * @params v the view where the button is
         * @params songs list of songs to play
         *
         */
        btnPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).playAll(v,songs);
            }
        });

/*        Song testSong= new Song("1","Ein Prosit","http://www.noiseaddicts.com/samples_1w72b820/2553.mp3", "0","0");
//        songs.add(testSong);
//        testSong= new Song("2","katyusha","http://www.noiseaddicts.com/samples_1w72b820/1450.mp3", "0","0");
//        songs.add(testSong);
//        testSong= new Song("3","Real national anthem","http://www.noiseaddicts.com/samples_1w72b820/4250.mp3", "0","0");
//        songs.add(testSong);
//        testSong= new Song("4","Egyptian  anthem","http://www.noiseaddicts.com/samples_1w72b820/4051.mp3", "0","0");
//        songs.add(testSong);
//        testSong= new Song("5","Dancin","https://www.mboxdrive.com/dancin%202.mp3", "0","0");
//        songs.add(testSong);*/
        /**
         * sets this class's song list to the one in navigation activity, then sets the adapter
         * to the list view that shows the songs in the list
         */

        SongListAdapter adapter = new SongListAdapter(this,songs);
        songListView.setAdapter(adapter);

        /**
         * makes each item in the dynamic song list view clickable, and able to play
         */
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button addSonToPlaylist = view.findViewById(R.id.songListView_addSongToPlaylistBtn);

                playASong(position,SongList.super.getParentFragment());

            }
        });
        return v;
    }

    /**
     * This method is called whenever a song is clicked in a song list
     *
     * @param position the order of the song in the list
     * @param fragment fragment where the song list is located
     */
    public void playASong(int position,Fragment fragment){

        Song song = songs.get(position);
        String songAddress = song.getUrl();
        String songName = song.getTitle();
        if (song.inQueue){
            ((NavigationActivity) fragment.requireActivity()).currentSongIndex=song.numberInQueue;
        }
        else {
            if (((NavigationActivity) fragment.requireActivity()).songQueue.size() > 0) {
                ((NavigationActivity) fragment.requireActivity()).currentSongIndex =
                        ((NavigationActivity) fragment.requireActivity()).songQueue.size();
                song.numberInQueue=((NavigationActivity)fragment.requireActivity()).currentSongIndex;
            }
            ((NavigationActivity) fragment.requireActivity()).songQueue.add(song);
            song.inQueue=true;
            // ((NavigationActivity)requireActivity()).setSongNameNav(songName);
        }
        ((NavigationActivity) fragment.requireActivity()).startStreamingService(songAddress);
    }
}
