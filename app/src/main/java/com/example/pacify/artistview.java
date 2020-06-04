package com.example.pacify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Used to make a list of song objects to play
 */
public class artistview extends Fragment {



String name ;
TextView artistname;
ImageView artistpic;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v= inflater.inflate(R.layout.artist_view, container, false);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            name=bundle.getString("key");
        }

        artistname = v.findViewById(R.id.artistName);
        artistpic = v.findViewById(R.id.artistPhoto);
        if (name == "Adele"){
            artistpic.setImageResource(R.drawable.adele);
        }else if(name =="Emeniem"){
            artistpic.setImageResource(R.drawable.emspotify);
        }
        artistname.setText(name);
        /**
         * Play All button, calls playAll function in NavigationActivity
         * @params view the view where the button is
         * @params songs list of songs to play
         *
         */

        return v;
    }


}
