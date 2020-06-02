package com.example.pacify;
/**
 * Imported songs from the database use this class to store its attributes
 * like URL, Likes, Number of plays, etc...
 *
 * */
public class Song {
    String id;
    String title;
    String url;
    int numPlays;
    int numLikes;
    Boolean isLiked=false;

    /**
     * setting all the attributes of the song
     * @param id song ID
     * @param title song title
     * @param url song url
     * @param numPlays number of times the song has been played
     * @param numLikes number of times the song has been liked
     */
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
