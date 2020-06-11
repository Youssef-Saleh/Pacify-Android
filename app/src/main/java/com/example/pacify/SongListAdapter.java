package com.example.pacify;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pacify.Utilities.Constants;

import java.util.List;

/**
 * bridge between loading song playlists and UI,
 * takes the song playlist and shows all songs in a vertical list in the UI.
 * Each song has a clickable add button that has different behaviour depending
 * on the user type (free or premium)
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


    public View getView (final int position, View convertView, ViewGroup parent){
        View v =convertView;

        if(convertView==null) {
            v = inflater.inflate(R.layout.song_list_row, parent, false);
        }
        final Song song= songs.get(position);


        TextView title=(TextView) v.findViewById(R.id.songListViewText);
        title.setText(song.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongList songList = new SongList(songs);
                songList.playASong(position,fragment);
            }
        });


        ImageView pic=(ImageView) v.findViewById(R.id.songListViewImage);
        if (song.artist.equals("AlanWalker")){
            pic.setImageResource(R.drawable.ignite);
        }else if( song.artist.equals("Tones and I")){
            pic.setImageResource(R.drawable.dancemonkey);
        }else if( song.artist.equals("bulow")){
            pic.setImageResource(R.drawable.ownme);
        }else if( song.artist.equals("billie eilish")){
            pic.setImageResource(R.drawable.badguy);
        }else if( song.artist.equals("Sia")){
            pic.setImageResource(R.drawable.sia);
        }else if( song.artist.equals("Martin Garrix")){
            pic.setImageResource(R.drawable.martingarix);
        }else if( song.artist.equals("Eminem")){
            pic.setImageResource(R.drawable.emspotify);
        }else if( song.artist.equals("Alan Walker")){
            pic.setImageResource(R.drawable.alanwalker);
        }else if( song.artist.equals("Adele")){
            pic.setImageResource(R.drawable.adele);
        }else if( song.artist.equals("Ed Sheeran")){
            pic.setImageResource(R.drawable.ed);
        }else if( song.artist.equals("Marshmello")){
            pic.setImageResource(R.drawable.marshmello);
        }
        else {
            pic.setImageResource(R.drawable.jazzicon);
        }
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongList songList = new SongList(songs);
                songList.playASong(position,fragment);
            }
        });


        Button AddButton = v.findViewById(R.id.songListView_addSongToPlaylistBtn);
        AddButton.getBackground().setTint(Constants.USER_TYPE.equals("free")? Color.DKGRAY
                : Color.WHITE);


        AddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!Constants.USER_TYPE.equals("free")) {
                    ((NavigationActivity) fragment.requireActivity())
                            .openAddSongToPlaylistDialog(song);
                }
                if(Constants.USER_TYPE.equals("free")){
                    Toast.makeText(((NavigationActivity) fragment.requireActivity())
                            , "Adding a song to  playlist is a premium feature"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
