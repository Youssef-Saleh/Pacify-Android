package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * Search fragment that is opened when clicking on search icon in navigation bar
 */
public class SearchFragment extends Fragment {
    ImageButton btnPop;
    ImageButton btnElectronic;
    ImageButton btnRock;
    ImageButton btnHipHop;
    ImageButton btnArabic;
    ImageButton btnParty;
    ImageButton btnJazz;
    List<Song> mysongs = new ArrayList<>();
    private RequestQueue requestQueue;
    ListView songsListView;

    /**
     * parses JSON files fetched from the url to song objects
     * It handles exceptions if the it cannot reach the url given.
     * @param url URL where JSON file is located
     */
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
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    /**
     * creates a new SongList fragment and replaces the current fragment inside the navigation view
     * to the new one.
     * It shows the list of the songs after getting the songs from JSON file
     */
    public void showSongList(){

        Fragment fragment= new SongList();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    /**
     * method that is called when the POP button is called.
     * calls theJsonParser method with the url to pop songs JSON (currently all songs are set as pop)
     * It sets the songs list in the navigation activity to the fetched song list
     * @param view view that was clicked
     */
    public void popGenre(View view){
        theJsonParser(Constants.PLAYLIST_ID.POP);
        ((NavigationActivity)getActivity()).songs=mysongs;
    }

    /**
     * called when pressing ELECTRONIC button is called
     * @param view view that was clicked
     */
    public void electronicGenre(View view){
        Toast.makeText(getActivity(), "Electronic: No Playlist To Show", Toast.LENGTH_SHORT).show();
    }

    public void rockGenre(View view){
        Toast.makeText(getActivity(), "Rock", Toast.LENGTH_SHORT).show();
    }

    public void hiphopGenre(View view){
        Toast.makeText(getActivity(), "Hip Hop", Toast.LENGTH_SHORT).show();
    }

    public void arabicGenre(View view){
        Toast.makeText(getActivity(), "Arabic", Toast.LENGTH_SHORT).show();
    }

    public void partyGenre(View view){
        Toast.makeText(getActivity(), "Party", Toast.LENGTH_SHORT).show();
    }

    public void chartsGenre(View view){
        Toast.makeText(getActivity(), "Jazz", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        /*View w= inflater.inflate(R.layout.song_list_view, container, false);
        songsListView= (ListView) w.findViewById(R.id.songListView);*/
        /**
         * creates a new requestQueue from volley
         */
        requestQueue=Volley.newRequestQueue(this.getContext());
        btnPop=v.findViewById(R.id.btnPop);
        btnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popGenre(v);

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
        btnJazz=v.findViewById(R.id.btnJazz);
        btnJazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartsGenre(v);
            }
        });


        return v;
    }


}
