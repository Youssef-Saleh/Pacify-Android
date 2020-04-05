package com.example.pacify;

public class Song {
    int id;
    String title;
    String url;
    int numPlays;
    int numLikes;


    public  Song(String id,String title,String url,String numPlays,String numLikes){
        this.id=Integer.parseInt(id);
        this.numPlays=Integer.parseInt(numPlays);
        this.numLikes=Integer.parseInt(numLikes);
        this.url=url;
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

    public String getUrl(){
        return url;
    }
    public int getNumLikes(){
        return numLikes;
    }
}
