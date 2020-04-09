package com.example.pacify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.media.AudioManager;
import android.widget.TextView;

import com.example.pacify.Settings.EditProfileFragment;
import com.example.pacify.Settings.Edit_profile.ChangePhoneNumber;
import com.example.pacify.Settings.Edit_profile.ChangeUserCountry;
import com.example.pacify.Settings.Edit_profile.ChangeUserDoB;
import com.example.pacify.Settings.Edit_profile.ChangeUserEmail;
import com.example.pacify.Settings.Edit_profile.ChangeUserGender;
import com.example.pacify.Settings.Edit_profile.ChangeUserPassword;
import com.example.pacify.Settings.MySettingsFragment;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class NavigationActivity extends AppCompatActivity {

    List<Song> songs;
    int currentSongIndex=0;
    Boolean shuffleSong = false;
    Boolean loopSong = false;
    boolean songLiked;

    public void playAll(View view,List<Song> playlist){
        songs = playlist;
        Song song = songs.get(0);
        String songAdress=song.getUrl();
        String songName = song.getTitle();
        //setSongNameNav(songName);
        startStreamingService(songAdress);

    }
    public void playNext (){
        if (shuffleSong==true){
            Random random = new Random();
            currentSongIndex = random.nextInt(songs.size());
        }
        else
        {
            currentSongIndex += 1;
        }
        if (currentSongIndex > (songs.size()-1)) {
            currentSongIndex = 0;
        }

        Song song = songs.get(currentSongIndex);
        String songAdress=song.getUrl();
        String songName = song.getTitle();
        //setSongName(songName);
        startStreamingService(songAdress);
    }

    public void playPrevious (){

        currentSongIndex -= 1;

        if (currentSongIndex < 0) {
            currentSongIndex = songs.size()-1;
        }
        Song song = songs.get(currentSongIndex);
        String songAdress=song.getUrl();
        String songName = song.getTitle();
       // setSongName(songName);
        startStreamingService(songAdress);


    }

    public void setSongNameNav(final String songName){
        TextView smallBar = (TextView) findViewById(R.id.songName);
        TextView bigBar = (TextView) findViewById(R.id.bigSongName);
        smallBar.setText(songName);
        bigBar.setText(songName);

        mBoundService.setSongName(songName);

        }

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

    public void likeButton (View view){
        FloatingActionButton likeSmall = (FloatingActionButton) findViewById(R.id.likeButton);
        FloatingActionButton likeBig = (FloatingActionButton) findViewById(R.id.bigLikeButton);
        Song song = songs.get(currentSongIndex);
        songLiked =song.getIsLiked();

        if (songLiked == false)
        {
            songLiked = true;
            song.setIsLiked(songLiked);
            showIfLiked();
        }
        else if (songLiked == true){

            songLiked = false;
            song.setIsLiked(songLiked);
            showIfLiked();
        }

    }
    public void showIfLiked(){
        FloatingActionButton likeSmall = (FloatingActionButton) findViewById(R.id.likeButton);
        FloatingActionButton likeBig = (FloatingActionButton) findViewById(R.id.bigLikeButton);
        Song song = songs.get(currentSongIndex);
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
    public void showIfLooping(){
        songLooping = (ImageButton) findViewById(R.id.playAgainButton);
        if (loopSong == false){
            songLooping.setImageResource(R.drawable.repeatoff);
        }
        else {
            songLooping.setImageResource(R.drawable.repeaton);

        }

    }
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
    public void isLooping(View view){
        if (loopSong == false){

            loopSong = true ;
            mBoundService.loopSong(loopSong);
            showIfLooping();
        }
        else {
            loopSong = false ;
            mBoundService.loopSong(loopSong);
            showIfLooping();
        }
    }

    public void isOnShuffle (View view){
        songShuffle = (ImageButton)findViewById(R.id.shuffleButton);
        if (shuffleSong == false)
        {
            shuffleSong=true;
            showIfShuffle();
        }
        else {
            shuffleSong=false;
            showIfShuffle();

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
    private BroadcastReceiver mMessageRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isPlaying = intent.getBooleanExtra("isPlaying",false);
            flipPlayPauseButton(isPlaying);
        }
    };

    private  BroadcastReceiver playerMassenger = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentPos = intent.getIntExtra("CurrentLocation",0);
            int maxLocation = intent.getIntExtra("Maxtime",0);
            progressBarUpdate(currentPos,maxLocation);
        }
    };

    private  BroadcastReceiver isFinished = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean isFinished = intent.getBooleanExtra("isFinished",false);
            if (isFinished == true)
                playNext();

        }
    };
    private  BroadcastReceiver prevPressed = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean prevPressed = intent.getBooleanExtra("Prev",false);
            if (prevPressed == true)
                playPrevious();

        }
    };
    private  BroadcastReceiver updateNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean update = intent.getBooleanExtra("Update",false);

                Song song = songs.get(currentSongIndex);
                String songName = song.getTitle();
                setSongNameNav(songName);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Bundle bundle = getIntent().getExtras();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        /*if (accessToken != null) {
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
                    mBoundService.togglePlayer();
            }
        });
        bigPlayPauseButton = (FloatingActionButton) findViewById((R.id.bigPlay));
        bigPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoundService.togglePlayer();
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
                if (fromUser)
                mBoundService.seekPlayer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                mBoundService.togglePlayer();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBoundService.togglePlayer();
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

    }
    public void startStreamingService(String url)
    {
        showIfLiked();
        showIfShuffle();
        showIfLooping();
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


    public  void flipPlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            bigPlayPauseButton.setImageResource(R.drawable.bigpause);
        } else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play );
            bigPlayPauseButton.setImageResource(R.drawable.bigplay );

        }
    }
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LibraryFragment()).commit();
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

    public boolean ConfirmEmailChange(String newEmail){
        //TODO(Adham): Change email
        return true;
    }

    public boolean ConfirmPasswordChange(String oldPassword, String newPassword){
        //TODO(Adham): Change password
        return true;
    }

    public boolean ConfirmPhoneChange(String newNumber) {
        //TODO(Adham): Change phone number
        return true;
    }

    public boolean ConfirmCountryChange(String newCountry) {
        //TODO(Adham): Change Country
        return true;
    }

    public boolean ConfirmGenderChange(String gender) {
        //TODO(Adham): Change Gender
        return true;
    }

    public boolean ConfirmDobChange(int year, int month, int day) {
        //TODO(Adham): Change DoB
        return true;
    }

    public void LogOut(){
        PreferenceUtilities.saveState("false", this);
        PreferenceUtilities.saveEmail("",this);
        PreferenceUtilities.savePassword("",this);
        PreferenceUtilities.saveUserName("",this);

        LoginManager.getInstance().logOut();

        Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
