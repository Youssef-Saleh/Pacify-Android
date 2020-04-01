package com.example.pacify;

public class Song {
    int id;
    String title;
    int numPlays;
    int numLikes;


    public  Song(String id,String title,String numPlays,String numLikes){
        this.id=Integer.parseInt(id);
        this.numPlays=Integer.parseInt(numPlays);
        this.numLikes=Integer.parseInt(numLikes);
        this.title=title;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public int getNumPlays(){
        return numPlays;
    }

    public int getNumLikes(){
        return numLikes;
    }
}
