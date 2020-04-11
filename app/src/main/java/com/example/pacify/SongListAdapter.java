package com.example.pacify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * bridge between loading song playlists and UI,
 * takes the song playlist and shows all songs in a vertical list in the UI
 */
public class SongListAdapter extends BaseAdapter {
    private Fragment fragment;
    //private Activity activity;
    private List<Song> songs;
    private  LayoutInflater inflater = null;

    public SongListAdapter (Fragment f , List<Song> s){
        fragment=f;
        songs=s;
        //inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) f.getLayoutInflater();
    }

    public int getCount(){
        return songs.size();
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
            v = inflater.inflate(R.layout.song_list_row, parent, false);
        }

        TextView title=(TextView) v.findViewById(R.id.songListViewText);
        Song song= songs.get(position);
        title.setText(song.getTitle());
        return v;
    }
}
