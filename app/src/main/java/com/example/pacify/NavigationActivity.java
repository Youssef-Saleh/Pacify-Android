package com.example.pacify;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pacify.Settings.EditProfileFragment;
import com.example.pacify.Settings.Edit_profile.ChangePhoneNumber;
import com.example.pacify.Settings.Edit_profile.ChangeUserCountry;
import com.example.pacify.Settings.Edit_profile.ChangeUserDoB;
import com.example.pacify.Settings.Edit_profile.ChangeUserEmail;
import com.example.pacify.Settings.Edit_profile.ChangeUserGender;
import com.example.pacify.Settings.Edit_profile.ChangeUserPassword;
import com.example.pacify.Settings.Go_Premium.GoPremiumStep1Fragment;
import com.example.pacify.Settings.Go_Premium.GoPremiumStep2Fragment;
import com.example.pacify.Settings.Go_Premium.GoPremiumStep3Fragment;
import com.example.pacify.Settings.MySettingsFragment;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class NavigationActivity extends AppCompatActivity
        implements CreatePlaylistDialog.CreatePlaylistDialogListener ,
        AddSongToPlaylistDialog.AddSongToPlaylistDialogListener {

    List<Song> songsToShow=new ArrayList<>();
    List<Song> songQueue= new ArrayList<>();
    List <Song> recentlyPlayed = new ArrayList<>(4);
    int recentlyPlayedIndex = 0;
    Song firstSong;
    Song secondSong;
    Song thirdSong;
    Song forthSong;
    Playlist likedSongs = new Playlist("Liked Songs");
    int currentSongIndex=0;
    Boolean shuffleSong = false;
    Boolean loopSong = false;
    boolean songLiked;
    Artist artistOne = new Artist("Sia", 1532);
    Artist artistTwo = new Artist("Martin Garrix", 934);
    Artist artistThree = new Artist("Eminem", 1002);
    Artist artistFour = new Artist("Alan Walker", 1230);
    Artist artistFive = new Artist("Adele",532);
    Artist artistSix = new Artist("Ed Sheeran", 1323);
    Artist artistSeven = new Artist("Marshmello", 1543);
    public String NewPlaylistName = "";
    public List<Playlist> playlists_nav = new ArrayList<>();
    private Song SongToBeAddedToPlaylist;
    DownloadManager downloadManager;


    public void downloadSong(View v){
        try {
            Song song = songQueue.get(currentSongIndex);
            String songURL = song.getUrl();
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(songURL);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long reference = downloadManager.enqueue(request);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void shareSong(View v){
        try{
            Song song= songQueue.get(currentSongIndex);
            String songURL = song.getUrl();
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = songURL;
            String shareSub = "Your Subject here";
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(myIntent, "Share Link!"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * plays the songs in the sent playlist from the beginning
     * @param view view that was clicked
     * @param playlist playlist that  we want played from the beginning
     */
    public void playAll(View view,List<Song> playlist){
        try {
            if (songQueue != playlist) {
                songQueue.clear();
                songQueue = new ArrayList<Song>(playlist);
                for (int i = 0; i < songQueue.size(); i++) {
                    songQueue.get(i).numberInQueue = i;
                }
            }
            currentSongIndex = 0;
            Song song = songQueue.get(currentSongIndex);
            String songAdress = song.getUrl();
            String songName = song.getTitle();
            //setSongNameNav(songName);
            startStreamingService(songAdress);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    ImageView bigImage;
    /**
     * This method is responsible for changung the player picture according to the song's artist.
     * */
    public void changePlayerPicture(){
        bigImage = (ImageView)findViewById(R.id.bigSongImage);
        Song song = songQueue.get(currentSongIndex);
        String songArtist = song.getArtist();
        if (songArtist.equals("AlanWalker")){
            bigImage.setImageResource(R.drawable.ignite);
        }else if( songArtist.equals("Tones and I")){
            bigImage.setImageResource(R.drawable.dancemonkey);
        }else if( songArtist.equals("bulow")){
            bigImage.setImageResource(R.drawable.ownme);
        }else if( songArtist.equals("billie eilish")){
            bigImage.setImageResource(R.drawable.badguy);
        }else if( songArtist.equals("Sia")){
            bigImage.setImageResource(R.drawable.sia);
        }else if( songArtist.equals("Martin Garrix")){
            bigImage.setImageResource(R.drawable.martingarix);
        }else if( songArtist.equals("Eminem")){
            bigImage.setImageResource(R.drawable.emspotify);
        }else if( songArtist.equals("Alan Walker")){
            bigImage.setImageResource(R.drawable.alanwalker);
        }else if( songArtist.equals("Adele")){
            bigImage.setImageResource(R.drawable.adele);
        }else if( songArtist.equals("Ed Sheeran")){
            bigImage.setImageResource(R.drawable.ed);
        }else if( songArtist.equals("Marshmello")){
            bigImage.setImageResource(R.drawable.marshmello);
        }
    }
    /**
     * This method is responsible for filling the recently played array accourding to the latest song being played
     * @param mod used to indicate if 0 means the user didn't play any songs yet so we fill the array with 4 random songs and if one we fill it with the song being played
     *
      */
    public void fillRecentlyPlayed( int mod){
        if (mod == 0){
            firstSong = new Song("1","Ignite","http://104.47.139.19/api/audio/Ignite.mp3",5,5);
            firstSong.setArtist("AlanWalker");
            recentlyPlayed.add(0,firstSong);
             secondSong = new Song("2","Dance Monkey","http://104.47.139.19/api/audio/DanceMonkey.mp3",5,5);
             secondSong.setArtist("Tones and I");
            recentlyPlayed.add(1,secondSong);
             thirdSong = new Song("3","Own Me","http://104.47.139.19/api/audio/OwnMe.mp3",5,5);
             thirdSong.setArtist("bulow");
            recentlyPlayed.add(2,thirdSong);
             forthSong = new Song("4","Bad Guy","http://104.47.139.19/api/audio/BadGuy.mp3",5,5);
             forthSong.setArtist("billie eilish");
            recentlyPlayed.add(3,forthSong);
        }else if (mod  == 1){
            if (recentlyPlayedIndex > 3){
                recentlyPlayedIndex = 0;
            }
            recentlyPlayed.remove(recentlyPlayedIndex);
            recentlyPlayed.add(recentlyPlayedIndex,songQueue.get(currentSongIndex));
            recentlyPlayedIndex +=1 ;
        }
    }
    /**
     * This method is called whenever the user presses the next button on the player , the songs finishes to play the next in the Queue,or next on notification.
     * It increments the song index and make sure it doesn't exeed the size oh the song list
     * if the shuffle Boolean is on, it generates a random index
     */
    public void playNext (){
        try{
            if (shuffleSong==true){
                Random random = new Random();
                currentSongIndex = random.nextInt(songQueue.size());
            }
            else
            {
                currentSongIndex += 1;
            }
            if (currentSongIndex > (songQueue.size()-1)) {
                currentSongIndex = 0;
            }

            Song song = songQueue.get(currentSongIndex);
            String songAdress=song.getUrl();
            String songName = song.getTitle();
            //setSongName(songName);
            startStreamingService(songAdress);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * This method is called whenever the user presses the prev button on the player or the notification
     * It decrements the song index and make sure it doesn't get less than 0
     */

    public void playPrevious (){
        try{
            currentSongIndex -= 1;

            if (currentSongIndex < 0) {
                currentSongIndex = songQueue.size()-1;
            }
            Song song = songQueue.get(currentSongIndex);
            String songAdress=song.getUrl();
            String songName = song.getTitle();
           // setSongName(songName);
            startStreamingService(songAdress);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * This method is called whenever the a new song is sent to be played
     * It sets the player bar with the name of the song and notification too
     * @param songName
     */
    public void setSongNameNav(final String songName, String name){
        TextView smallBar = (TextView) findViewById(R.id.songName);
        TextView bigBar = (TextView) findViewById(R.id.bigSongName);
        TextView artistName = (TextView) findViewById(R.id.artistName);
        smallBar.setText(songName);
        bigBar.setText(songName);
        artistName.setText(name);
        changePlayerPicture();
        mBoundService.setSongName(songName);
        mBoundService.setIcon(name);

        }
    /**
     * This method is called whenever the user presses the small player bar or the bigger one to shift between them
     * @param view
     */
    public void playFab(View view){
        BottomNavigationView playerNav=(BottomNavigationView)findViewById(R.id.playNav);
        NavigationView bigPlayer = (NavigationView)findViewById(R.id.bigPlayer) ;
        //FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.menimizeWindow);

        if (bigPlayer.getVisibility()== view.INVISIBLE){
            bigPlayer.setVisibility(View.VISIBLE);
            playerNav.setVisibility((View.INVISIBLE));
        }
        else{
            bigPlayer.setVisibility(View.INVISIBLE);
            playerNav.setVisibility(View.VISIBLE);
        }
    }
    /**
     * This method is responsible for sending the post request for liking a song.
     * @param url the url to the mockserver
     * Acknowledgment to https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put
     *  */
    private void theJsonParser(String url){
        RequestQueue queue = Volley.newRequestQueue(NavigationActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Song song = songQueue.get(currentSongIndex);
                        // response
                        Toast.makeText(NavigationActivity.this,"Request Successfully sent to Like / Unlike  "+ song.getTitle(),Toast.LENGTH_SHORT).show();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NavigationActivity.this,"Request Failed, the status will be saved locally",Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("Like", "Like/Dislike Request");
                return params;
            }
        };
        queue.add(postRequest);
    }
    /**
     * This method is called whenever the user presses the like button to like the song
     * it sets the (Like boolean) in the song object according to the user choice
     * @param view
     */

    public void likeButton (View view){
        try {
            theJsonParser(Constants.POST_REQUEST);
            FloatingActionButton likeSmall = (FloatingActionButton) findViewById(R.id.likeButton);
            FloatingActionButton likeBig = (FloatingActionButton) findViewById(R.id.bigLikeButton);
            Song song = songQueue.get(currentSongIndex);
            songLiked = song.getIsLiked();

            if (songLiked == false) {
                songLiked = true;
                song.setIsLiked(songLiked);
                likedSongs.addSong(song);
                showIfLiked();
            } else if (songLiked == true) {

                songLiked = false;
                likedSongs.removeSong(song);
                song.setIsLiked(songLiked);
                showIfLiked();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * This method is called after the user likes a song to update the player bar
     */
    public void showIfLiked(){
        FloatingActionButton likeSmall = (FloatingActionButton) findViewById(R.id.likeButton);
        FloatingActionButton likeBig = (FloatingActionButton) findViewById(R.id.bigLikeButton);
        Song song = songQueue.get(currentSongIndex);
        songLiked =song.getIsLiked();


        if (songLiked == false)
        {
            likeSmall.setImageResource(R.drawable.nolikeheart);
            likeBig.setImageResource(R.drawable.nolikeheart);

        }
        else if (songLiked == true){
            likeSmall.setImageResource(R.drawable.likeheart);
            likeBig.setImageResource(R.drawable.likeheart);

        }

    }
    /**
     * This method is called after the user presses loop  song to update the player bar
     */
    public void showIfLooping(){
        songLooping = (ImageButton) findViewById(R.id.playAgainButton);
        if (loopSong == false){
            songLooping.setImageResource(R.drawable.repeatoff);
        }
        else {
            songLooping.setImageResource(R.drawable.repeaton);

        }

    }
    /**
     * This method is called after the user presses shuffle  song to update the player bar
     */
    public void showIfShuffle(){
            songShuffle = (ImageButton)findViewById(R.id.shuffleButton);
        if (shuffleSong == false)
        {
            songShuffle.setImageResource(R.drawable.shuffleoff);
        }
        else {
            songShuffle.setImageResource(R.drawable.shuffleonpng);

        }

    }
    /**
     * This method is called when the user selects to loop a current song
     * @param view
     */

    public void isLooping(View view){
        try {
            if (loopSong == false) {

                loopSong = true;
                mBoundService.loopSong(loopSong);
                showIfLooping();
            } else {
                loopSong = false;
                mBoundService.loopSong(loopSong);
                showIfLooping();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * This method is called when the user selects to shuffle the playlist
     */
    public void isOnShuffle (View view){
        songShuffle = (ImageButton)findViewById(R.id.shuffleButton);
        if (shuffleSong == false)
        {
            shuffleSong=true;
            try {
                showIfShuffle();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            shuffleSong=false;
            try {
                showIfShuffle();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    ImageButton songShuffle;
    ImageButton songLooping;
    ImageButton playPauseButton ;
    ImageButton playNext;
    ImageButton playPrevious;
    FloatingActionButton bigPlayPauseButton;
    PlayerService mBoundService;
    boolean mServiceBound = false;

    AudioManager audioManager;
    /**
     * This is the method that makes the service connection between this activity and playservice
     * it creates a new PlayerService mBinder that can call functions in the service when called here.
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.MyBinder myBinder = (PlayerService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        mServiceBound = false ;
        }
    };
    /**
     * This is the Broadcast Receiver that receives messages and intents sent from the service to this activity
     * This particular Receiver is used to check if the media player is playing or nor and change the player bar accordingly
     */
    private BroadcastReceiver mMessageRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPlaying = intent.getBooleanExtra("isPlaying",false);
            flipPlayPauseButton(isPlaying);
        }
    };
    /**
     * This particular Receiver is used to update the seekBar with the progress of the song and seek song according to where the user moves it
     */
    private  BroadcastReceiver playerMassenger = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentPos = intent.getIntExtra("CurrentLocation",0);
            int maxLocation = intent.getIntExtra("Maxtime",0);
            progressBarUpdate(currentPos,maxLocation);
        }
    };
    /**
     * This particular Receiver is used to receive a check from service when the song is finished to call next player function and play next song
     * It's also used if the user pressed next song button on the notification
     */
    private  BroadcastReceiver isFinished = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean isFinished = intent.getBooleanExtra("isFinished",false);
            if (isFinished == true)
                playNext();

        }
    };
    /**
     * This Receiver is used whenever the user presses the prev button on the notification to call prev Function.
     */
    private  BroadcastReceiver prevPressed = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean prevPressed = intent.getBooleanExtra("Prev",false);
            if (prevPressed == true)
                playPrevious();

        }
    };
    /**
     * This Recevier has a significant importance as it signals when the service is created and now we can change the notificaiton text according to the song.
     * Without it the app will crash if we tried to sit the notification context with song name.
     */
    private  BroadcastReceiver updateNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean update = intent.getBooleanExtra("Update",false);

                Song song = songQueue.get(currentSongIndex);
                String songName = song.getTitle();
                String artistName = song.getArtist();

            View view = null;
            setSongNameNav(songName, artistName);

        }
    };

    public String UserName;

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = getIntent().getExtras();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            UserName = bundle.getString("fb_username");
        }else {
            UserName = bundle.getString("username");
        }
    }
    /**
     * This function is called when this activity is created after a successful log in
     * it also sets the volume bar that controls the app volume
     * it sets the play/pause button to communicate with the service through mBoundService
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        /*Bundle bundle = getIntent().getExtras();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            UserName = bundle.getString("fb_username");
        }else {
            UserName = bundle.getString("username");
        }*/

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        playPauseButton= (ImageButton) findViewById(R.id.playPauseBarButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBoundService.togglePlayer();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        bigPlayPauseButton = (FloatingActionButton) findViewById((R.id.bigPlay));
        bigPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBoundService.togglePlayer();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        //String url="http://www.noiseaddicts.com/samples_1w72b820/4250.mp3";
        //startStreamingService(url);
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume((AudioManager.STREAM_MUSIC));
         SeekBar volumeControl = (SeekBar)findViewById(R.id.volumeBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    try{
                        mBoundService.seekPlayer(progress);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                try{
                    mBoundService.togglePlayer();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try{
                    mBoundService.togglePlayer();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        playNext= (ImageButton) findViewById(R.id.nextSong);
        playNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
            }
        });
        playPrevious= (ImageButton) findViewById(R.id.prevSong);
        playPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrevious();
            }
        });
        fillRecentlyPlayed(0);

    }
    /**
     * This is the method that starts the service and sets the foreground action to start
     * @param url the song url that's fitched from the song list and json data.
     *            it sets the shuffle, loop, like according to user choice .
     */
    public void startStreamingService(String url)
    {
        loopSong = false ;
        showIfLiked();
        showIfShuffle();
        showIfLooping();
        fillRecentlyPlayed(1);
        Intent i = new Intent(this,PlayerService.class);
        i.putExtra("url",url) ;
        i.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(i);
        bindService(i,mServiceConnection,Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageRecevier
                ,new IntentFilter("changePlayButton"));
        LocalBroadcastManager.getInstance(this).registerReceiver(playerMassenger
                ,new IntentFilter("scrubberUpdates"));
        LocalBroadcastManager.getInstance(this).registerReceiver(isFinished
                ,new IntentFilter("isFinished"));
        LocalBroadcastManager.getInstance(this).registerReceiver(prevPressed
                ,new IntentFilter("Prev"));
        LocalBroadcastManager.getInstance(this).registerReceiver(updateNotification
                ,new IntentFilter("Update"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageRecevier);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerMassenger);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(isFinished);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(prevPressed);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateNotification);
    }

    /**
     * This is the ui version of flipPlay/Pause that changes the buttons according to
     * the media player state
     * @param isPlaying that's received from the broadcast receiver and were passed from the service
     */
    public  void flipPlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            bigPlayPauseButton.setImageResource(R.drawable.bigpause);
        } else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play );
            bigPlayPauseButton.setImageResource(R.drawable.bigplay );

        }
    }
    /**
     * This is the function that's called from the broadcast receiver that receives the song updates
     * and update the seekbar accordingly.
     * @param currentPosition the current sec of the song being played
     * @param duration the length of the song to set the max of the bar with it
     */
    public void progressBarUpdate(final int currentPosition, final int duration){
        final SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar);

        scrubber.setMax(duration);
        scrubber.setProgress(currentPosition);
        /*if (scrubber.getMax()==scrubber.getProgress()){
            playNext();
        }*/

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new LibraryFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void GoBackFromSettings(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LibraryFragment())
                .commit();
    }

    public void openGoPremiumStep1Fragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new GoPremiumStep1Fragment())
                .commit();
    }

    public void openGoPremiumStep2Fragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new GoPremiumStep2Fragment())
                .commit();
    }

    public void openGoPremiumStep3Fragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new GoPremiumStep3Fragment())
                .commit();
    }

    public void OpenSettingsFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MySettingsFragment())
                .commit();
    }

    public void GoToEditProfile(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new EditProfileFragment())
                .commit();
    }

    public void GoToEditEmail(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangeUserEmail())
                .commit();
    }

    public void GoToEditPassword(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangeUserPassword())
                .commit();
    }

    public void GoToEditPhoneNumber(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangePhoneNumber())
                .commit();
    }

    public void GoToEditCounty(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangeUserCountry())
                .commit();
    }

    public void GoToEditGender(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangeUserGender())
                .commit();
    }

    public void GoToEditDoB(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ChangeUserDoB())
                .commit();
    }

    public void upgradeUserToPremium() {
        toChange = "type";
        changedObject = "premium";
        ApplyChange();

        com.example.pacify.Utilities.Constants.USER_TYPE = "premium";
        Toast.makeText(getBaseContext(), "You're  Premium now\nEnjoy!"
                , Toast.LENGTH_SHORT).show();
    }


    /**
     * Called when the user enter a name and press create, it check if it already
     * exist or not, if not it create a new one with the name entered by the user
     * @param playlistName is the playlist name typed in the dialog
     */
    @Override
    public void sendPlaylistName(String playlistName) {
        NewPlaylistName = playlistName;

        for(int i=0;i<playlists_nav.size();i++){
            if(playlists_nav.get(i).getTitle().equals(NewPlaylistName)) {
                Toast.makeText(this
                        , "Playlist '" + NewPlaylistName + "' already exists"
                        , Toast.LENGTH_SHORT).show();
                return;
            }
        }

        createPlaylistRequest(NewPlaylistName);

        Toast.makeText(this
                , "Playlist '" + NewPlaylistName + "' is created"
                , Toast.LENGTH_SHORT).show();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LibraryFragment())
                .commit();

        Playlist playlist = new Playlist(playlistName);
        playlists_nav.add(playlist);
    }

    private void createPlaylistRequest(final String plName){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.CREATE_PLAYLIST;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("playlist").equals("successful")){
                                Toast.makeText(NavigationActivity.this, "Playlist is " +
                                        "created successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(NavigationActivity.this, "An Error occurred," +
                                " playlist will be created temporarily", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("playlist", plName);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(postRequest);
    }

    /**
     * Opens a dialog to enter the new playlist name
     */
     public void openCreatePlaylistDialog() {
        CreatePlaylistDialog createPlaylistDialog = new CreatePlaylistDialog();
        createPlaylistDialog.show(getSupportFragmentManager(), "Playlist Dialog");
    }

    /**
     * Called when the user enter a name and press create, it check if the playlist exist
     * or not, only if it exists it check if the song is already in that playlist or not
     * and if not it adds the song to the playlist
     * @param playlistName is the playlist name typed in the dialog
     */
    @Override
    public void sendPlaylistNameToAddSong(String playlistName) {
        boolean songInPlaylist = false;
        for(int i=0; i < playlists_nav.size(); i++) {
            if(playlistName.equals(playlists_nav.get(i).getTitle())){
                for(int j=0; j < playlists_nav.get(i).playlistSongs.size(); j++){
                    songInPlaylist = (playlists_nav.get(i).playlistSongs.get(j)
                            == SongToBeAddedToPlaylist) || songInPlaylist;
                }
                if (!songInPlaylist){
                    playlists_nav.get(i).addSong(SongToBeAddedToPlaylist);
                    Toast.makeText(this
                            , "Song added"
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this
                            , "This song is already in playlist '"
                                    + playlists_nav.get(i).title + "'"
                            , Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        Toast.makeText(this
                , "Playlist '" + playlistName + "' is not found"
                , Toast.LENGTH_SHORT).show();
    }

    /**
     * Opens a dialog to add a playlist name to add song to
     * @param song is the song to be added to a playlist
     */
    public void openAddSongToPlaylistDialog(Song song) {
        SongToBeAddedToPlaylist = song;
        AddSongToPlaylistDialog addSongToPlaylistDialog = new AddSongToPlaylistDialog();
        addSongToPlaylistDialog.show(getSupportFragmentManager(), "Add song to playlist dialog");
    }

    public void SendEmailRequest(final String vCode , final String mail) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SEND_EMAIL;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("sent").equals("successful")){
                                Toast.makeText(NavigationActivity.this, "Email " +
                                        "sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(NavigationActivity.this, "An Error occurred," +
                                " no email is sent", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", mail);
                params.put("VerCode", vCode);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(postRequest);
    }

    /**
     * Sending a request to change password
     * @param newEmail new Email entered by the user
     * @return true if email changed successfully
     */
    public boolean ConfirmEmailChange(final String newEmail){
        RequestQueue queue = Volley.newRequestQueue(this);
        final boolean[] successful = new boolean[1];
        successful[0] = true;
        String url = Constants.PROFILE_CHANGE_EMAIL;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("changed").equals("successful")){
                                Toast.makeText(NavigationActivity.this, "Email " +
                                        "changed successfully", Toast.LENGTH_SHORT).show();
                                successful[0] = true;
                            }
                            else{
                                Toast.makeText(NavigationActivity.this, "Email is not" +
                                        " changed successfully", Toast.LENGTH_SHORT).show();
                                successful[0] = false;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NavigationActivity.this, "An Error occurred," +
                                " please try again", Toast.LENGTH_SHORT).show();
                        successful[0] = false;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("new_email", newEmail);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(postRequest);
        return successful[0];
    }

    /**
     * Sending a request to change password
     * @param oldPassword old password entered by the user
     * @param newPassword New password entered by the user
     * @return true if changed successfully
     */
    public boolean ConfirmPasswordChange(final String oldPassword, final String newPassword){
        RequestQueue queue = Volley.newRequestQueue(this);
        final boolean[] successful = new boolean[1];
        successful[0] = true;
        String url = Constants.PROFILE_CHANGE_PASSWORD;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("changed").equals("successful")){
                                Toast.makeText(NavigationActivity.this, "Password " +
                                        "changed successfully", Toast.LENGTH_SHORT).show();
                                successful[0] = true;
                            }
                            else{
                                Toast.makeText(NavigationActivity.this, "Password " +
                                        "doesn't match!", Toast.LENGTH_SHORT).show();
                                successful[0] = false;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(NavigationActivity.this, "An Error occurred," +
                                " please try again", Toast.LENGTH_SHORT).show();
                        successful[0] = false;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("old_pass", oldPassword);
                params.put("new_pass", newPassword);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(postRequest);
        return successful[0];
    }

    public boolean ConfirmPhoneChange(String newNumber) {
        toChange = "phone";
        changedObject = newNumber;
        return ApplyChange();
    }

    public boolean ConfirmCountryChange(String newCountry) {
        toChange = "country";
        changedObject = newCountry;
        return ApplyChange();
    }

    public boolean ConfirmGenderChange(String gender) {
        toChange = "gender";
        changedObject = gender;
        return ApplyChange();
    }

    /**
     * Reformatting the date to be the same as in the server.
     */
    public boolean ConfirmDobChange(int year, int month, int day) {
        toChange = "dop";

        DecimalFormat df = new DecimalFormat("##");
        changedObject = year + "-" + df.format(month) + "-" + day;

        return ApplyChange();
    }

    String toChange;
    String changedObject;
    boolean successful = true;
    MediaSession.Token tok;
    /**
     * A PUT request is send to the server, it works for country,
     * gender, phone number and date of birth, and the change is
     * applied to what 'toChange' string hold, and is changed to
     * what 'changedObject' string hold.
     * It return true if the operation was successful, false if not.
     */
    public boolean ApplyChange(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.EDIT_PROFILE_URL;

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            successful = response.getString("edit").equals("successful");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NavigationActivity.this, "An error occurred\n" +
                                      ",please try again.", Toast.LENGTH_SHORT).show();
                        successful = false;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(toChange, changedObject);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        queue.add(putRequest);
        return successful;
    }


    /**
     * It delete the saved user data, logout the user from facebook (if he was logged
     * in using facebook) and reset the app.
     * Source: https://stackoverflow.com/a/17166729/12963182
     */
    public void LogOut(){

        PreferenceUtilities.saveState("false", this);
        PreferenceUtilities.saveEmail("",this);
        PreferenceUtilities.savePassword("",this);
        PreferenceUtilities.saveUserName("",this);

        LoginManager.getInstance().logOut();

        Intent mStartActivity = new Intent(this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,
                mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    ///////////////
    }

    @Override
    public void onBackPressed() {

    }


}
