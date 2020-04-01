package com.example.pacify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.LocaleList;
import android.util.Log;
import android.widget.SeekBar;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerService extends Service {

    MediaPlayer mediaPlayer=new MediaPlayer();
    private final IBinder mBinder = new MyBinder();


    public class MyBinder extends Binder{
        PlayerService getService(){
            return PlayerService.this;
        }
    }

    public PlayerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra("url") != null)
            playStream(intent.getStringExtra("url"));

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)){
            Log.d("info", "Service Started");
            showNotification();
        }
        else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)){
            Log.d("info", "Prev Pressed");

        }
        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)){
            Log.d("info", "Play Pressed");

            togglePlayer();
        }
        else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)){
            Log.d("info", "Next Pressed");
        }
        else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)){
            Log.d("info", "Stop Recieved");
            stopForeground(true);
            stopSelf();
        }
        return START_NOT_STICKY;
    }
    public   void sendPlayerStatus (){
        if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
            int currentLocation = mediaPlayer.getCurrentPosition();
            int maxLocation = mediaPlayer.getDuration();
            Intent playerStat = new Intent("scrubberUpdates");
            playerStat.putExtra("CurrentLocation", currentLocation);
            playerStat.putExtra("Maxtime", maxLocation);

            LocalBroadcastManager.getInstance(this).sendBroadcast(playerStat);

        }

    }

   /* private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.pacify";
        String channelName = "Pacify";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);

        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Intent notificationIntent = new Intent(this, NavigationActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
       // notificationIntent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Intent prevIntent = new Intent(this, PlayerService.class);
        prevIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent pPrevIntent = PendingIntent.getActivity(this,0,prevIntent,0);

        Intent playIntent = new Intent(this, PlayerService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pPlayIntent = PendingIntent.getActivity(this,0,playIntent,0);

        Intent nextIntent = new Intent(this, PlayerService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pNextIntent = PendingIntent.getActivity(this,0,nextIntent,0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.tree);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("TreeCify")
                .setTicker("Playing Oh Yeah Music")
                .setContentText("Shalood Song")
                .setSmallIcon(R.drawable.tree)
                .setLargeIcon(Bitmap.createScaledBitmap(icon,128,128,false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous,"Previous",pPrevIntent)
                .addAction(android.R.drawable.ic_media_play,"Play",pPlayIntent)
                .addAction(android.R.drawable.ic_media_next,"Next",pNextIntent)
                .build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);
    }*/
    private void showNotification(){

        String NOTIFICATION_CHANNEL_ID = "com.example.pacify";
        Intent notificationIntent = new Intent(this, NavigationActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
       // notificationIntent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Intent prevIntent = new Intent(this, PlayerService.class);
        prevIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent pPrevIntent = PendingIntent.getService(this,0,prevIntent,0);

        Intent playIntent = new Intent(this, PlayerService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pPlayIntent = PendingIntent.getService(this,0,playIntent,0);

        Intent nextIntent = new Intent(this, PlayerService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pNextIntent = PendingIntent.getService(this,0,nextIntent,0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.tree);


        int playPauseButtonId= android.R.drawable.ic_media_play;
        String playOrPause= "Play";
        if (mediaPlayer!=null && mediaPlayer.isPlaying())
        {
            playPauseButtonId=android.R.drawable.ic_media_pause;
            playOrPause="Pause";
        }
        Notification notification = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("TreeCify")
                .setContentText("Shalood Song")
                .setSmallIcon(R.drawable.tree)
                .setLargeIcon(Bitmap.createScaledBitmap(icon,128,128,false))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous,"Previous",pPrevIntent)
                .addAction(playPauseButtonId,playOrPause,pPlayIntent)
                .addAction(android.R.drawable.ic_media_next,"Next",pNextIntent)
                .build();

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
          // startMyOwnForeground();
     //   else
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);

    }
    @Override
    public IBinder onBind(Intent intent) {
       return mBinder ;
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
                    flipPlayPauseButton(false);
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
            flipPlayPauseButton(false);
            showNotification();
        }catch (Exception e){
            Log.d("Info","Infooo");
        }
    }
    public void playPlayer(){
        try {
            mediaPlayer.start();
            flipPlayPauseButton(true);
            showNotification();
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendPlayerStatus();
                }
            },0,2);



        }catch (Exception e){
            Log.d("Info","Infooo");
        }
    }
    public void flipPlayPauseButton(boolean isPlaying){
        // code to commuinicate with navigation thread
        Intent intent = new Intent("changePlayButton");
        // add data
        intent.putExtra("isPlaying",isPlaying);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
    public void seekPlayer(int seekValue){
        mediaPlayer.seekTo(seekValue);
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
