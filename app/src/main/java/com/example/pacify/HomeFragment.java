package com.example.pacify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageButton btnPop;
    Boolean Liked;
    Boolean ifFollowed;
    String CurrentArtist;
    ImageButton sia;
    ImageButton martin;
    ImageButton ed;
    ImageButton marshemello;
    ImageButton alan;
    ImageButton adele;
    ImageButton moodOne;
    ImageButton moodTwo;
    ImageButton moodthree;
    ImageButton moodFour;
    ImageButton discoverWeekly;
    ImageButton mixOne;
    ImageButton mixTwo;
    ImageButton mixThree;
    ImageButton help;
    List<Song> mysongs = new ArrayList<>();
    private RequestQueue requestQueue;
    String currentGenre;
    String currentArtist;
    String currentMood;
    ListView songsListView;
    TextView songOne;
    TextView songTwo;
    TextView songThree;
    TextView songFour;
    ImageButton songPicture;

    Bundle bundle = new Bundle();
    @SuppressLint("ResourceType")

    public void showSongList(int mod) {

    if (mod == 0 ){
        Fragment fragment= new artistview();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();}
    else if (mod == 1){
        Fragment fragment= new SongList(mysongs);
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }
    else if (mod == 2){
        showSongList(1);
    }

    }



    private void theJsonParser(String url, final int mod){
        //String url="https://cat-fact.herokuapp.com/facts/random";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject playlistSong = response.getJSONObject(i);

                                String id= playlistSong.getString("_id");
                                String songName = playlistSong.getString("name");
                                String songUrl = playlistSong.getString("url");
                                String songGenre = playlistSong.getString("genre");
                                int timesPlayed = playlistSong.getInt("timesPlayed");
                                int numLikes = playlistSong.getInt("rateCount");
                                String artistName = playlistSong.getString("artist");
                                String songMood = playlistSong.getString("mood");
                                Song thisSong= new Song(id,songName,songUrl, timesPlayed,numLikes);
                                thisSong.setArtist(artistName);
                                thisSong.setMood(songMood);
                               if (mod == 0){
                                if(currentArtist.equals(artistName)) {
                                    mysongs.add(thisSong);
                                }
                            }else if (mod == 1){
                                   if(currentMood.equals(songMood)) {
                                       mysongs.add(thisSong);
                               }
                            }else if (mod == 2){
                                   if(currentMood.equals(songMood)  ) {
                                       mysongs.add(thisSong);
                                   }else if (currentGenre.equals(songGenre)){
                                       mysongs.add(thisSong);
                                   }
                               }

                            }
                            showSongList(mod);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue= Volley.newRequestQueue(this.getContext());
        btnPop=v.findViewById(R.id.imageButton9);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistThree.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistThree.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Eminem";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;

            }
        });
        adele = v.findViewById((R.id.imageButton5));

        adele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistFive.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistFive.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Adele";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        sia = v.findViewById(R.id.imageButton11);
        sia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistOne.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistOne.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Sia";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;

            }
        });
        martin = v.findViewById(R.id.imageButton10);
        martin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistTwo.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistTwo.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Martin Garrix";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;

            }
        });
        alan = v.findViewById(R.id.imageButton8);
        alan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistFour.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistFour.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Alan Walker";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        ed= v.findViewById(R.id.imageButton7);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistSix.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistSix.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Ed Sheeran";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        marshemello = v.findViewById(R.id.imageButton6);
        marshemello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,0);
                ifFollowed =  ((NavigationActivity)getActivity()).artistSeven.isFollowed();
                bundle.putString("key",((NavigationActivity)getActivity()).artistSeven.name);
                bundle.putBoolean("isFollowed",ifFollowed);
                currentArtist="Marshmello";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        moodOne = v.findViewById(R.id.imageButton12);
        moodOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,1);

                currentMood="Sad";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        moodTwo = v.findViewById(R.id.imageButton13);
        moodTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,1);

                currentMood="Energetic";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        moodthree = v.findViewById(R.id.imageButton16);
        moodthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,1);

                currentMood="Funky";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        moodFour = v.findViewById(R.id.imageButton18);
        moodFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,1);

                currentMood="Happy";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        discoverWeekly = v.findViewById(R.id.imageButton19);
        discoverWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,2);
                currentMood="Happy";
                currentGenre="Electronic";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        mixOne = v.findViewById(R.id.imageButton20);
        mixOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,2);
                currentMood="Sad";
                currentGenre="Pop";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        mixTwo = v.findViewById(R.id.imageButton22);
        mixTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,2);
                currentGenre = "Happy";
                currentMood="Funky";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });
        mixThree = v.findViewById(R.id.imageButton23);
        mixThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theJsonParser(Constants.PLAYLIST_ID.POP,2);
                currentGenre = "HipHop";
                currentMood="Dark";
                ((NavigationActivity)getActivity()).songsToShow=mysongs;
            }
        });

        if(bundle != null){
            Liked = bundle.getBoolean("likeStat");
            CurrentArtist = bundle.getString("currentArtist");


        }
        updateRecentlyPlayed(v);

        songOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=((NavigationActivity)getActivity()).recentlyPlayed.get(0);
                String songAdress = song.getUrl();
                String songName = song.getTitle();
                if (song.inQueue){
                    ((NavigationActivity) getActivity()).currentSongIndex=song.numberInQueue;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);

                }
                else {
                    if (((NavigationActivity) getActivity()).songQueue.size() > 0) {
                        ((NavigationActivity) getActivity()).currentSongIndex =((NavigationActivity) getActivity()).songQueue.size();
                        song.numberInQueue=((NavigationActivity) getActivity()).currentSongIndex;
                    }
                    ((NavigationActivity) getActivity()).songQueue.add(song);
                    song.inQueue=true;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);
                    // ((NavigationActivity)getActivity()).setSongNameNav(songName);
                }
            }
        });
        songTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=((NavigationActivity)getActivity()).recentlyPlayed.get(1);
                String songAdress = song.getUrl();
                String songName = song.getTitle();
                if (song.inQueue){
                    ((NavigationActivity) getActivity()).currentSongIndex=song.numberInQueue;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);

                }
                else {
                    if (((NavigationActivity) getActivity()).songQueue.size() > 0) {
                        ((NavigationActivity) getActivity()).currentSongIndex =((NavigationActivity) getActivity()).songQueue.size();
                        song.numberInQueue=((NavigationActivity) getActivity()).currentSongIndex;
                    }
                    ((NavigationActivity) getActivity()).songQueue.add(song);
                    song.inQueue=true;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);
                    // ((NavigationActivity)getActivity()).setSongNameNav(songName);
                }
            }
        });
        songThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=((NavigationActivity)getActivity()).recentlyPlayed.get(2);
                String songAdress = song.getUrl();
                String songName = song.getTitle();
                if (song.inQueue){
                    ((NavigationActivity) getActivity()).currentSongIndex=song.numberInQueue;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);

                }
                else {
                    if (((NavigationActivity) getActivity()).songQueue.size() > 0) {
                        ((NavigationActivity) getActivity()).currentSongIndex =((NavigationActivity) getActivity()).songQueue.size();
                        song.numberInQueue=((NavigationActivity) getActivity()).currentSongIndex;
                    }
                    ((NavigationActivity) getActivity()).songQueue.add(song);
                    song.inQueue=true;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);
                    // ((NavigationActivity)getActivity()).setSongNameNav(songName);
                }                        }
        });
        songFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=((NavigationActivity)getActivity()).recentlyPlayed.get(3);
                String songAdress = song.getUrl();
                String songName = song.getTitle();
                if (song.inQueue){
                    ((NavigationActivity) getActivity()).currentSongIndex=song.numberInQueue;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);

                }
                else {
                    if (((NavigationActivity) getActivity()).songQueue.size() > 0) {
                        ((NavigationActivity) getActivity()).currentSongIndex =((NavigationActivity) getActivity()).songQueue.size();
                        song.numberInQueue=((NavigationActivity) getActivity()).currentSongIndex;
                    }
                    ((NavigationActivity) getActivity()).songQueue.add(song);
                    song.inQueue=true;
                    ((NavigationActivity) getActivity()).startStreamingService(songAdress);
                    // ((NavigationActivity)getActivity()).setSongNameNav(songName);
                }                        }
        });
        help = v.findViewById(R.id.helpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new fragmen_help();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
            }
        });
        return v;
    }

    public void updateRecentlyPlayed(View v){
        songOne = v.findViewById(R.id.songOneName);
        songOne.setText(((NavigationActivity)getActivity()).recentlyPlayed.get(0).title);
        songTwo = v.findViewById(R.id.textView4);
        songTwo.setText(((NavigationActivity)getActivity()).recentlyPlayed.get(1).title);
        songThree = v.findViewById(R.id.textView6);
        songThree.setText(((NavigationActivity)getActivity()).recentlyPlayed.get(2).title);
        songFour = v.findViewById(R.id.textView7);
        songFour.setText(((NavigationActivity)getActivity()).recentlyPlayed.get(3).title);
        updateSongPicture(v,0);
        updateSongPicture(v,1);
        updateSongPicture(v,2);
        updateSongPicture(v,3);

    }
    public void updateSongPicture(View v, int mod){
        if (mod == 0){
            songPicture = v.findViewById(R.id.photoSongOne);
        }else if (mod == 1){
            songPicture = v.findViewById(R.id.photoSongTwo);
        }else if (mod == 2){
            songPicture = v.findViewById(R.id.photoSongThree);
        }else if (mod == 3){
            songPicture = v.findViewById(R.id.photoSongFour);
        }
        String artistName = ((NavigationActivity)getActivity()).recentlyPlayed.get(mod).getArtist();
        if (artistName.equals("Adele")){
            songPicture.setImageResource(R.drawable.adele);
        }else if(artistName.equals("Eminem")){
            songPicture.setImageResource(R.drawable.emspotify);
        }else if (artistName.equals("Sia")){
            songPicture.setImageResource(R.drawable.sia);
        }else if (artistName.equals("Martin Garrix")){
            songPicture.setImageResource(R.drawable.martingarix);
        }else if (((NavigationActivity) getActivity()).recentlyPlayed.get(mod).artist.equals("Alan Walker")){
            songPicture.setImageResource(R.drawable.alanwalker);
        }else if (((NavigationActivity) getActivity()).recentlyPlayed.get(mod).artist.equals("Ed Sheeran")){
            songPicture.setImageResource((R.drawable.ed));
        }else if (((NavigationActivity) getActivity()).recentlyPlayed.get(mod).artist.equals("Marshmello")){
            songPicture.setImageResource(R.drawable.marshmello);
        }else if (artistName.equals("AlanWalker")) {
            songPicture.setImageResource(R.drawable.ignite);
        }else if (artistName.equals("billie eilish")) {
            songPicture.setImageResource(R.drawable.badguy);
        }else if (artistName.equals("bulow")) {
            songPicture.setImageResource(R.drawable.ownme);
        }else if (artistName.equals("Tones and I")) {
            songPicture.setImageResource(R.drawable.dancemonkey);
        }

    }
}
