package com.example.pacify;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.util.Log;

import java.io.IOException;
public class Player {

    MediaPlayer mediaPlayer=new MediaPlayer();
   public static Player player;
   String url="";

   public Player(){
       this.player = this;
   }

   public void playStream(String url){
       if (mediaPlayer != null){
           try {

               mediaPlayer.stop();
           }catch (Exception e){

           }
           mediaPlayer=null;
       }
       mediaPlayer = new MediaPlayer();
       mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
       try{
           mediaPlayer.setDataSource(url);
           mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
               @Override
               public void onPrepared(MediaPlayer mp) {
                   playPlayer();
               }
           });
           mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
               @Override
               public void onCompletion(MediaPlayer mp) {
                   NavigationActivity.flipPlayPauseButton(false);
               }
           });
           mediaPlayer.prepareAsync();
       }catch (IOException e){
           e.printStackTrace();
       }
   }
   public void pausePlayer(){
       try {
           mediaPlayer.pause();
           NavigationActivity.flipPlayPauseButton(false);
       }catch (Exception e){
           Log.d("Info","Infooo");
       }
   }
    public void playPlayer(){
        try {
            mediaPlayer.start();
            NavigationActivity.flipPlayPauseButton(true);
        }catch (Exception e){
            Log.d("Info","Infooo");
        }
    }
    public void togglePlayer(){
       try {
           if (mediaPlayer.isPlaying())
               pausePlayer();
           else
               playPlayer();
       }catch (Exception e){
           Log.d("Infooo","infoe");
       }
    }
}
