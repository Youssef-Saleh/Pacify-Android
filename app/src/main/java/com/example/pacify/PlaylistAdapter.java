package com.example.pacify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * bridge between loading playlists and UI,
 * takes the list of playlists and shows them vertically in the UI
 */
public class PlaylistAdapter extends BaseAdapter {
    private Fragment fragment;
    //private Activity activity;
    private List<Playlist> playlists;
    private  LayoutInflater inflater = null;

    public PlaylistAdapter (Fragment f , List<Playlist> p){
        fragment=f;
        playlists=p;
        //inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) f.getLayoutInflater();
    }

    public int getCount(){
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public long getItemId (int position){
        return position;
    }


    public View getView (int position, View convertView, ViewGroup parent){
        View v =convertView;

        if(convertView==null) {
            v = inflater.inflate(R.layout.playlist_row, parent, false);
        }

        TextView title=(TextView) v.findViewById(R.id.playlistName);
        ImageView pic=(ImageView) v.findViewById(R.id.playlistImage);
        Playlist playlist= playlists.get(position);
        pic.setImageResource(R.drawable.rocking);
        title.setText(playlist.getTitle());
        return v;
    }
}
