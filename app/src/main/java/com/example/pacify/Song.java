package com.example.pacify;

public class Song {
    String id;
    String title;
    String url;
    int numPlays;
    int numLikes;
    Boolean isLiked=false;


    public  Song(String id,String title,String url,int numPlays,int numLikes){
        this.id=id;
        this.numPlays=numPlays;
        this.numLikes=numLikes;
        this.url=url;
        this.title=title;
    }

    public String getId(){
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

    public void setIsLiked(Boolean condition){isLiked=condition;}

    public Boolean getIsLiked(){return isLiked;}
}
