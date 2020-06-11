package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacify.Utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adham Mahmoud, Hisham Algendy
 * @version 1
 * This class (Fragment) hold the user playlists, song likes and settings
 */
public class LibraryFragment extends Fragment {

    private Button logOut;
    private Button createPlaylist;
    ListView playlistView;
    public List<Playlist> playlists=new ArrayList<>();

    public void showSongList(int position){

        Fragment fragment = new SongList(playlists.get(position).playlistSongs);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(!((NavigationActivity)requireActivity()).NewPlaylistName.equals(""))
            CreateNewPlaylist(((NavigationActivity)requireActivity()).NewPlaylistName);
        ((NavigationActivity)requireActivity()).NewPlaylistName = "";*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        logOut = view.findViewById(R.id.logout_button);
        createPlaylist = view.findViewById(R.id.playlist_add_playlist);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).OpenSettingsFragment();
            }
        });


        createPlaylist.setVisibility(Constants.USER_TYPE.equals("free")?View.INVISIBLE:View.VISIBLE);
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)requireActivity()).openCreatePlaylistDialog();
            }
        });

        playlistView=(ListView) view.findViewById(R.id.playlistView);

        Playlist likedPlaylist = ((NavigationActivity)requireActivity()).likedSongs;
        playlists.add(likedPlaylist);

         //////////////////////Test
        if(Constants.ADD_DUMMY_SONGS) {
            Song testSong = new Song("1", "hoi", "https://www.mboxdrive.com/Drake%20-%20Hotline%20Bling%20(Instrumental)%20(ReProd.%20By%20JDP).mp3", 0, 0);
            likedPlaylist.addSong(testSong);
            testSong = new Song("2", "katyusha", "http://www.noiseaddicts.com/samples_1w72b820/1450.mp3", 0, 0);
            likedPlaylist.addSong(testSong);
            testSong = new Song("3", "Real national anthem", "http://www.noiseaddicts.com/samples_1w72b820/4250.mp3", 0, 0);
            likedPlaylist.addSong(testSong);
            Constants.ADD_DUMMY_SONGS = false;
        }
        ///////////////////////

        //Adding the playlists created by the user
        playlists.addAll(((NavigationActivity) requireActivity()).playlists_nav);

        PlaylistAdapter adapter = new PlaylistAdapter(this,playlists);
        playlistView.setAdapter(adapter);

        /**
         * makes each item in the dynamic playlist view clickable, and able to play
         */
        playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playlist playlist=playlists.get(position);
                showSongList(position);
            }
        });

        return view;
    }
}
