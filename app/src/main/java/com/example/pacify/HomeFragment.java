package com.example.pacify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
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

    ImageButton adele;
    List<Song> mysongs = new ArrayList<>();
    private RequestQueue requestQueue;
    String currentGenre;
    ListView songsListView;
    @SuppressLint("ResourceType")

    public void showSongList(){Bundle bundle = new Bundle();
        ifFollowed =  ((NavigationActivity)getActivity()).artistThree.isFollowed();
        bundle.putString("key",((NavigationActivity)getActivity()).artistThree.name);
        bundle.putBoolean("isFollowed",ifFollowed);
        Fragment fragment= new artistview();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();


    }
    public void popGenre(View view){
        theJsonParser(Constants.PLAYLIST_ID.POP);
        currentGenre="Pop";
        ((NavigationActivity)getActivity()).songsToShow=mysongs;
    }
    private void theJsonParser(String url){
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

                                Song thisSong= new Song(id,songName,songUrl, timesPlayed,numLikes);
                                if(currentGenre.equals(songGenre)) {
                                    mysongs.add(thisSong);
                                }
                            }
                            showSongList();
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
                popGenre(v);


            }
        });
        adele = v.findViewById((R.id.imageButton5));

        adele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                ifFollowed =  ((NavigationActivity)getActivity()).artistFive.isFollowed();
                bundle.putBoolean("isFollowed",ifFollowed);
                bundle.putString("key",((NavigationActivity)getActivity()).artistFive.name);
                Fragment fragment= new artistview();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();

            }
        });
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Liked = bundle.getBoolean("likeStat");
            CurrentArtist = bundle.getString("currentArtist");


        }


        return v;
    }
}
