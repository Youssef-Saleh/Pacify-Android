package com.example.pacify;

public class Artist {
    String name;
    int numFollowers;
    Boolean followed=false;


    public  Artist(String name,int numFollowers){
        this.name=name;
        this.numFollowers=numFollowers;
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
    public void setIsFollowed(Boolean condition){followed=condition;}


}
