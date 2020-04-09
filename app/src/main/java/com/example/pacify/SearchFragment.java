package com.example.pacify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class SearchFragment extends Fragment {
    ImageButton btnPop;
    ImageButton btnElectronic;
    Button btnRock;
    Button btnHipHop;
    Button btnArabic;
    Button btnParty;
    Button btnCharts;
    List<Song> mysongs = new ArrayList<>();
    private RequestQueue requestQueue;
    ListView songsListView;

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
                                int timesPlayed = playlistSong.getInt("timesPlayed");
                                int numLikes = playlistSong.getInt("rateCount");

                                Song thisSong= new Song(id,songName,songUrl, timesPlayed,numLikes);
                                mysongs.add(thisSong);
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
            }
        });
        requestQueue.add(request);
    }

    public void showSongList(){

        Fragment fragment= new SongList();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }
    public void popGenre(View view){

        theJsonParser(Constants.PLAYLIST_ID.POP);
        ((NavigationActivity)getActivity()).songs=mysongs;


    }
    public void electronicGenre(View view){
        theJsonParser(Constants.PLAYLIST_ID.ELECTRONIC);
        ((NavigationActivity)getActivity()).songs=mysongs;
    }

    public void rockGenre(View view){
        Toast.makeText(getActivity(), "Rock", Toast.LENGTH_LONG).show();
    }

    public void hiphopGenre(View view){
        Toast.makeText(getActivity(), "Hip Hop", Toast.LENGTH_LONG).show();
    }

    public void arabicGenre(View view){
        Toast.makeText(getActivity(), "Arabic", Toast.LENGTH_LONG).show();
    }

    public void partyGenre(View view){
        Toast.makeText(getActivity(), "Party", Toast.LENGTH_LONG).show();
    }

    public void chartsGenre(View view){
        Toast.makeText(getActivity(), "Charts", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        View w= inflater.inflate(R.layout.song_list_view, container, false);
        songsListView= (ListView) w.findViewById(R.id.songListView);
        requestQueue=Volley.newRequestQueue(this.getContext());
        btnPop=v.findViewById(R.id.btnPop);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {

                popGenre(w);

            }
        });
        btnElectronic=v.findViewById(R.id.btnElectronic);
        btnElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electronicGenre(v);
            }
        });
        btnRock=v.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rockGenre(v);
            }
        });
        btnHipHop=v.findViewById(R.id.btnHipHop);
        btnHipHop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiphopGenre(v);
            }
        });
        btnArabic=v.findViewById(R.id.btnArabic);
        btnArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arabicGenre(v);
            }
        });
        btnParty=v.findViewById(R.id.btnParty);
        btnParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyGenre(v);
            }
        });
        btnCharts=v.findViewById(R.id.btnCharts);
        btnCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartsGenre(v);
            }
        });


        return v;
    }


}
