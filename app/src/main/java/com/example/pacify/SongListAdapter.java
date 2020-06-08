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
                songList.playASong(position);
            }
        });


        ImageView pic=(ImageView) v.findViewById(R.id.songListViewImage);
        pic.setImageResource(R.drawable.dualipa);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongList songList = new SongList(songs);
                songList.playASong(position);
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
