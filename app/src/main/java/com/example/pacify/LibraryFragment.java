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

import java.util.ArrayList;
import java.util.List;


public class LibraryFragment extends Fragment {

    private Button logOut;
    ListView playlistView;
    public List<Playlist> playlists=new ArrayList<>();

    public void showSongList(int position){

        SongList mySongList= new SongList(playlists.get(position).playlistSongs);

        Fragment fragment = mySongList;
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        logOut = view.findViewById(R.id.logout_button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)getActivity()).OpenSettingsFragment();
            }
        });

        playlistView=(ListView) view.findViewById(R.id.playlistView);



        Playlist likedPlaylist= new Playlist("Liked Songs");
        likedPlaylist=((NavigationActivity)getActivity()).likedSongs;
        playlists.add(likedPlaylist);


        Playlist testPlaylist2= new Playlist("User Created (test)");
        playlists.add(testPlaylist2);

        PlaylistAdapter adapter = new PlaylistAdapter(this,playlists);
        playlistView.setAdapter(adapter);

        /**
         * makes each item in the dynamic song list view clickable, and able to play
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
