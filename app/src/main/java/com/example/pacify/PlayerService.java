package com.example.pacify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Abdulrahman Hammouda
 * @version 2
 * PlayerService class that extends Service
 * It handles everything about foreground service, player notification, media player, audio interruptions and audio focus.
 */
public class PlayerService extends Service {
    Boolean isFinished= false;
    String songName = "";
    MediaPlayer mediaPlayer=new MediaPlayer();
    private final IBinder mBinder = new MyBinder();

    /**
     * This is the MyBinder class that is used to communicate with navigation activity
     */
    public class MyBinder extends Binder{
        PlayerService getService(){
            return PlayerService.this;
        }
    }

    public PlayerService() {
    }

    /**
     * This onStart command is fired whenever the service is started
     * @param flags if there's any flags, but in our case we don't use them
     * @param intent the intent that carries the message and info about the action , url for example.
     * @param startId  we don't use it aswell in our case
     */
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
            sendPrev();

        }
        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)){
            Log.d("info", "Play Pressed");

            togglePlayer();
        }
        else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)){
            sendIsFinished();
            Log.d("info", "Next Pressed");
        }
        else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)){
            Log.d("info", "Stop Recieved");
            stopForeground(true);
            stopSelf();
        }
        return START_NOT_STICKY;

    }
    /**
     * This onDestroy command is fired whenever the user closes the app, logs out.
     * It kills the service and remove the notification
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent endService = new Intent(this,PlayerService.class);

        endService.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);

    }
    /**
     * This is where we send that broadcast that carries information about song current location and max duration to update the seekBar
     */
    public   void sendPlayerStatus (){
        try {
            if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
                int currentLocation = mediaPlayer.getCurrentPosition();
                int maxLocation = mediaPlayer.getDuration();
                Intent playerStat = new Intent("scrubberUpdates");
                playerStat.putExtra("CurrentLocation", currentLocation);
                playerStat.putExtra("Maxtime", maxLocation);

                LocalBroadcastManager.getInstance(this).sendBroadcast(playerStat);

            }
        }catch(Exception e){

        }

    }
    /**
     * This is where we send that broadcast that carries information when the song is finished
     */
    public void sendIsFinished () {

            Intent songStatus = new Intent("isFinished");
            songStatus.putExtra("isFinished", true);
            LocalBroadcastManager.getInstance(this).sendBroadcast(songStatus);

    }
    /**
     * This is where we send that broadcast that carries information that the user pressed prev button on the notification
     */
    public void sendPrev (){
        Intent prevIntent = new Intent("Prev");
        prevIntent.putExtra("Prev", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(prevIntent);
    }
    /**
     * This is where we send that broadcast that carries information that the service is created and now we can update the notification name.
     */
    public void updateNotification(){
        Intent updateNotification = new Intent( "Update");
        updateNotification.putExtra("Update",true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateNotification);
    }
    /**
     * This is the function that builds the notification for devices having higher android build versions
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.pacify";
        String channelName = "Pacify";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);
        chan.setDescription("no sound");
        chan.setSound(null,null);
        chan.enableLights(false);
        chan.setLightColor(Color.BLUE);
        chan.enableVibration(false);
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
        Notification notification = notificationBuilder
                .setOngoing(false)
                .setContentTitle("Pacify")
                .setContentText(songName)
                .setSmallIcon(R.drawable.songimage)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setLargeIcon(Bitmap.createScaledBitmap(icon,128,128,false))
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.ic_media_previous,"Previous",pPrevIntent)
                .addAction(playPauseButtonId,playOrPause,pPlayIntent)
                .addAction(android.R.drawable.ic_media_next,"Next",pNextIntent)
                .build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification);
    }
    /**
     * This is the function that builds the notification for devices having lower android build versions
     */
    private void showNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else {
            String NOTIFICATION_CHANNEL_ID = "com.example.pacify";
            Intent notificationIntent = new Intent(this, NavigationActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            // notificationIntent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Intent prevIntent = new Intent(this, PlayerService.class);
            prevIntent.setAction(Constants.ACTION.PREV_ACTION);
            PendingIntent pPrevIntent = PendingIntent.getService(this, 0, prevIntent, 0);

            Intent playIntent = new Intent(this, PlayerService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);

            Intent nextIntent = new Intent(this, PlayerService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pNextIntent = PendingIntent.getService(this, 0, nextIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.tree);


            int playPauseButtonId = android.R.drawable.ic_media_play;
            String playOrPause = "Play";
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                playPauseButtonId = android.R.drawable.ic_media_pause;
                playOrPause = "Pause";
            }
            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setOngoing(false)
                    .setContentTitle("Pacify")
                    .setContentText(songName)
                    .setSmallIcon(R.drawable.songimage)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setLargeIcon(Bitmap.createScaledBitmap(icon,128,128,false))
                    .setContentIntent(pendingIntent)
                    .addAction(android.R.drawable.ic_media_previous,"Previous",pPrevIntent)
                    .addAction(playPauseButtonId,playOrPause,pPlayIntent)
                    .addAction(android.R.drawable.ic_media_next,"Next",pNextIntent)
                    .build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
       return mBinder ;
    }
    /**
     * This is the method that starts the media player
     * @param url the url that's sent from navigation activity according to the chosen song
     */

    public void playStream(String url){
        if (mediaPlayer != null){
            try {

                mediaPlayer.reset();
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
                    //mediaPlayer.start();

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                    flipPlayPauseButton(false);

                    sendIsFinished();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
            mediaPlayer.prepareAsync();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * This is the function that pauses the media player and calls the flipPlay/Pause button according to the state of media player.
     * it also updates the notification
     */
    public void pausePlayer(){
        try {
            mediaPlayer.pause();
            flipPlayPauseButton(false);
            showNotification();
            unregisterReceiver(noisyAudioStreamReceiver);
        }catch (Exception e){
            Log.d("Info","Infooo");
        }
    }
    /**
     * This fucntion calls getAudioFoucusAndPlay function to get the audio focus of the device and start playing the music
     * it also updates the notification
     * @version 3
     */
    public void playPlayer(){
        try {
            getAudioFoucusAndPlay();
            flipPlayPauseButton(true);
            updateNotification();
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
    /**
     * This is the service version of flip function.
     * it sends a broadcast to navigation activity to update the player bar.
     * @param isPlaying states the state of the media player
     */
    public void flipPlayPauseButton(boolean isPlaying){
        // code to commuinicate with navigation thread
        Intent intent = new Intent("changePlayButton");
        // add data
        intent.putExtra("isPlaying",isPlaying);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
    /**
     *This funcion is called by navigation activity when the user chooses to loop the song or not.
     * @param isLooping a booelen passed from the activity with the user's choice
     */

    public void loopSong(boolean isLooping){
       try {
           if (mediaPlayer != null)
           {mediaPlayer.setLooping(isLooping);}
       }catch (Exception e ){
           e.printStackTrace();
       }
    }
    /**
     *This funcion is called by navigation activity when the user seeks the seekbar to certain position so the music will play from that position.
     */
    public void seekPlayer(int seekValue){
        mediaPlayer.seekTo(seekValue);
    }
    /**
     * This function toggels between play and pause and updates the notification accordingly.
     */
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
    /**
     *This function is called by the navigation activity when it receives that the service is started to update the notification with the song name.
     * @param navName the song name.
     */
    public void setSongName(String navName){
            songName = navName;
            showNotification();
    }
    // audio focus section
    private AudioManager am;
    private boolean playingBeforeInterruption = false;
    /**
     * This is the function that gets the audio focus of the device and starts the media player
     * it also handles interruption like (phone calls, another music app is opened , etc...)
     * This function is extremly important so avoid any strange behaviors from the app and any crashes.
     */
    public void getAudioFoucusAndPlay (){
        am = (AudioManager) this.getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        // request audio focus
        int result = am.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            mediaPlayer.start();
            registerReceiver(noisyAudioStreamReceiver,intentFilter);
        }
    }

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                if (mediaPlayer.isPlaying()){
                    playingBeforeInterruption = true;
                } else {
                    playingBeforeInterruption = false;
                }
                pausePlayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                if (playingBeforeInterruption == true)
                    playPlayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                pausePlayer();
                am.abandonAudioFocus(afChangeListener);
            }
        }
    };

    // audio rerouted
    /**
     * This function handels when the audio becomes noisy and when the user unplugs the headphones.
     */
    private class NoisyAudioStreamReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())){
                pausePlayer();
            }
        }
    }

    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private NoisyAudioStreamReceiver noisyAudioStreamReceiver = new NoisyAudioStreamReceiver();

}
