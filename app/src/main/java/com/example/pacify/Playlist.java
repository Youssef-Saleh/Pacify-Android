package com.example.pacify;

import java.util.ArrayList;
import java.util.List;


public class Playlist {

        String title;
        List<Song> playlistSongs= new ArrayList<>() ;

        /**
         * setting all attributes of playlist
         * @param title playlist title
         *
         */
        public  Playlist(String title){//, List<Song> songList){
            this.title=title;
            //this.playlistSongs=songList;
        }

        public String getTitle(){
            return title;
        }

        public void addSong(Song song){
            playlistSongs.add(song);
        }

        public void removeSong(Song song){
            playlistSongs.remove(song);
        }

        public List<Song> getPlaylistSongs() {
            return playlistSongs;
        }

    public void addSong(List<Song> songList){
            playlistSongs.addAll(songList);
        }



}

