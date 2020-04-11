package com.example.pacify;

import android.media.AudioManager;
import android.media.MediaPlayer;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ShuffleTest(){
        NavigationActivity navigationActivity = new NavigationActivity();
        Boolean shuffle = navigationActivity.shuffleSong;

        if (shuffle == false){
            navigationActivity.isOnShuffle();
            assertEquals(true,navigationActivity.shuffleSong);
        }
        else {
            navigationActivity.isOnShuffle();
            assertEquals(false,navigationActivity.shuffleSong);
        }
    }
@Test
    public void flipPlayPauseTest(){
        PlayerService service = new PlayerService();
        service.loopSong(true);


}
    @Test
    public void toggleTest(){
        PlayerService service = new PlayerService();
        service.togglePlayer();

    }
    @Test
    public void mediaPlayerTest() {
       MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource("http://23.96.41.241:5000/audio/Abdulrahman_rap_clean.mp3");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(true, mediaPlayer.isPlaying());

                   /* service.loopSong(true);

                    assertEquals(false, service.mediaPlayer.isLooping());
                    service.loopSong(false);
                    assertEquals(false, service.mediaPlayer.isLooping());
                    int duration = 200;
                    service.seekPlayer(duration);
                    assertEquals(200, service.mediaPlayer.getCurrentPosition());
                    service.mediaPlayer.pause();
                    assertEquals(false, service.mediaPlayer.isPlaying());*/
    }}