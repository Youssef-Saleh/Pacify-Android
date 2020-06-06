package com.example.pacify;

import java.util.List;

public class Artist {
    String name;
    int numFollowers;
    Boolean followed=false;
    List<Song> artistSongs;


    public  Artist(String name,int numFollowers,List<Song> Songs){
        this.name=name;
        this.numFollowers=numFollowers;
        this.artistSongs=Songs;

    }

    public String getName(){
        return name;
    }

    public Boolean isFollowed(){
        return followed;
    }

    public int getNumFollowers(){
        return numFollowers;
    }

    public List<Song> getList(){
        return artistSongs;
    }


}
