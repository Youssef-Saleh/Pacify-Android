package com.example.pacify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.AudioManager;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NavigationActivity extends AppCompatActivity {

    public void playFab(View view){
        BottomNavigationView playerNav=(BottomNavigationView)findViewById(R.id.playNav);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        if (playerNav.getVisibility()== view.INVISIBLE){
            playerNav.setVisibility(View.VISIBLE);
            playerNav.animate().alpha(0.7f).setDuration(1000);
            fab.setImageResource(android.R.drawable.stat_sys_download);
        }
        else{
            playerNav.setVisibility(View.INVISIBLE);
            playerNav.animate().alpha(0f).setDuration(500);
            fab.setImageResource(android.R.drawable.stat_sys_upload);


        }
    }

    static ImageButton playPauseButton ;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        playPauseButton= (ImageButton)findViewById(R.id.playPauseBarButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound){
                    mBoundService.togglePlayer();

                }
            }
        });

        String url="https://secure21.syncusercontent.com/mfs-60:b6bafe6205587ae39a5b2645ed47e17a=============================/p/Abdo3azma_Corona%20(online-audio-converter.com).mp3?allowdd=0&datakey=Wct5MWHYjAShNV95b9Ku7n6QGbRt1BWqVV5hPLRVGykBu7zhZ8j6U3/iFAuzj9ZMy6C5uMDJ+7j9AulruvdtOO07wFbgm8xj1pkX16G9MOZsbHeYUJ0oWVVScty3bk16nO1qOgKHhBXj8miRDCqfvpqg9lW1c0ZjrtxKS6E6JjggeApGUzSfMnCGqG0emyF8ofuLfAMIQDq/zSE2rCu52kGELCgDKTYQ0bPqEbJYYEG6VFcWUexgtKC7/iOUEqGdOQTGGmE6/FAa1pLOwewNjTQPVl5c45ThnmeERSJGVSCa52Bq6JbziY5L68l/FLUS0omzzv3LUKE/TrTvgpze6g&engine=ln-3.1.31&errurl=F/yAA2/EFtOATd+SQVHyVA2gYESwBd2LwD0aa0VQJ3IUanQAjpmEQNwXDPeF94WbNhqWQAUG8tv7lLXfzf03fimmtC/2jDQE4ZJl8SmtWaQ3PW6JsLUxjQ4WbZSivU4r2VRsEqBoxXaceQ7kg6TJu6K+C48lIwvm61Mf8cXhLdvunj5/eUl4s5brKqx2Me0Y4VqR3PEzljEgf0O6htPa2Ia9j+ezy5uLUcGH78LrnFkfELGQzpcSWZeIap8g/0NizVNj+JSdzF4Y2iV5l5MY7MPG/q6HHcKnvR2J/fbzSIYD2cxMdVbfsUIHkQUToEy9NNVI9yEEuIAwXQQRw4GCXg==&header1=Q29udGVudC1UeXBlOiBhdWRpby9tcGVn&header2=Q29udGVudC1EaXNwb3NpdGlvbjogaW5saW5lOyBmaWxlbmFtZT0iQWJkbzNhem1hX0Nvcm9uYSUyMChvbmxpbmUtYXVkaW8tY29udmVydGVyLmNvbSkubXAzIjtmaWxlbmFtZSo9VVRGLTgnJ0FiZG8zYXptYV9Db3JvbmElMjAob25saW5lLWF1ZGlvLWNvbnZlcnRlci5jb20pLm1wMzs&ipaddress=68f899f035261119843b278bd4dc159ca54e8415&linkcachekey=042f25820&linkoid=1105370009&mode=100&sharelink_id=3478773060009&timestamp=1585697349372&uagent=a55e5ff7b5a5bd370e5f2751eabd22df4ac4f3dd&signature=348abbffdec55321869f8539f636eac9843b0b02&cachekey=60:b6bafe6205587ae39a5b2645ed47e17a=============================";
        startStreamingService(url);
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
    }
private void startStreamingService(String url)
{
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
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageRecevier,new IntentFilter("changePlayButton"));
        LocalBroadcastManager.getInstance(this).registerReceiver(playerMassenger,new IntentFilter("scrubberUpdates"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageRecevier);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerMassenger);
    }


    public static void flipPlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play );

        }
    }
    public void progressBarUpdate(final int currentPosition, final int duration){
        final SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar);

        scrubber.setMax(duration);
        scrubber.setProgress(currentPosition);

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

    public void OpenSettingsFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MySettingsFragment())
                .commit();
    }

    public void LogOut(){
        PreferenceUtilities.saveState("false", this);
        PreferenceUtilities.saveEmail("",this);
        PreferenceUtilities.savePassword("",this);
        PreferenceUtilities.saveUserName("",this);

        Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void GoBackFromSettings(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
               new LibraryFragment()).commit();
    }

    @Override
    public void onBackPressed() {

    }
}
