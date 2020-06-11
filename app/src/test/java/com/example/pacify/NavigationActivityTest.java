package com.example.pacify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import static org.mockito.Mockito.*;

class NavigationActivityTest {
    @Mock
    Song firstSong;
    @Mock
    Song secondSong;
    @Mock
    Song thirdSong;
    @Mock
    Song forthSong;
    @Mock
    Playlist likedSongs;
    @Mock
    Song SongToBeAddedToPlaylist;
    @InjectMocks
    NavigationActivity navigationActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDownloadSong() {
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");

        navigationActivity.downloadSong(null);
    }

    @Test
    void testShareSong() {
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");

        navigationActivity.shareSong(null);
    }

    @Test
    void testPlayAll() {
        when(firstSong.getTitle()).thenReturn("getTitleResponse");
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getTitle()).thenReturn("getTitleResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getTitle()).thenReturn("getTitleResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getTitle()).thenReturn("getTitleResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getTitle()).thenReturn("getTitleResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.playAll(null, Arrays.<Song>asList(new Song("id", "title", "url", 0, 0)));
    }

    @Test
    void testChangePlayerPicture() {
        when(firstSong.getArtist()).thenReturn("getArtistResponse");
        when(secondSong.getArtist()).thenReturn("getArtistResponse");
        when(thirdSong.getArtist()).thenReturn("getArtistResponse");
        when(forthSong.getArtist()).thenReturn("getArtistResponse");
        when(SongToBeAddedToPlaylist.getArtist()).thenReturn("getArtistResponse");

        navigationActivity.changePlayerPicture();
    }

    @Test
    void testFillRecentlyPlayed() {
        navigationActivity.fillRecentlyPlayed(1);
    }

    @Test
    void testPlayNext() {
        when(firstSong.getTitle()).thenReturn("getTitleResponse");
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getTitle()).thenReturn("getTitleResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getTitle()).thenReturn("getTitleResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getTitle()).thenReturn("getTitleResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getTitle()).thenReturn("getTitleResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.playNext();
    }

    @Test
    void testPlayPrevious() {
        when(firstSong.getTitle()).thenReturn("getTitleResponse");
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getTitle()).thenReturn("getTitleResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getTitle()).thenReturn("getTitleResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getTitle()).thenReturn("getTitleResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getTitle()).thenReturn("getTitleResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.playPrevious();
    }

    @Test
    void testSetSongNameNav() {
        when(firstSong.getArtist()).thenReturn("getArtistResponse");
        when(secondSong.getArtist()).thenReturn("getArtistResponse");
        when(thirdSong.getArtist()).thenReturn("getArtistResponse");
        when(forthSong.getArtist()).thenReturn("getArtistResponse");
        when(SongToBeAddedToPlaylist.getArtist()).thenReturn("getArtistResponse");

        navigationActivity.setSongNameNav("songName", "name");
    }

    @Test
    void testLikeButton() {
        when(firstSong.getTitle()).thenReturn("getTitleResponse");
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getTitle()).thenReturn("getTitleResponse");
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getTitle()).thenReturn("getTitleResponse");
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getTitle()).thenReturn("getTitleResponse");
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getTitle()).thenReturn("getTitleResponse");
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.likeButton(null);
    }

    @Test
    void testShowIfLiked() {
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.showIfLiked();
    }

    @Test
    void testIsLooping() {
        navigationActivity.isLooping(null);
    }

    @Test
    void testOnCreate() {
        when(firstSong.getTitle()).thenReturn("getTitleResponse");
        when(firstSong.getUrl()).thenReturn("getUrlResponse");
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getTitle()).thenReturn("getTitleResponse");
        when(secondSong.getUrl()).thenReturn("getUrlResponse");
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getTitle()).thenReturn("getTitleResponse");
        when(thirdSong.getUrl()).thenReturn("getUrlResponse");
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getTitle()).thenReturn("getTitleResponse");
        when(forthSong.getUrl()).thenReturn("getUrlResponse");
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getTitle()).thenReturn("getTitleResponse");
        when(SongToBeAddedToPlaylist.getUrl()).thenReturn("getUrlResponse");
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.onCreate(null);
    }

    @Test
    void testStartStreamingService() {
        when(firstSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(secondSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(thirdSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(forthSong.getIsLiked()).thenReturn(Boolean.TRUE);
        when(SongToBeAddedToPlaylist.getIsLiked()).thenReturn(Boolean.TRUE);

        navigationActivity.startStreamingService("url");
    }

    @Test
    void testFlipPlayPauseButton() {
        navigationActivity.flipPlayPauseButton(true);
    }

    @Test
    void testProgressBarUpdate() {
        navigationActivity.progressBarUpdate(0, 0);
    }

    @Test
    void testSendPlaylistName() {
        when(likedSongs.getTitle()).thenReturn("getTitleResponse");

        navigationActivity.sendPlaylistName("playlistName");
    }

    @Test
    void testSendPlaylistNameToAddSong() {
        when(likedSongs.getTitle()).thenReturn("getTitleResponse");

        navigationActivity.sendPlaylistNameToAddSong("playlistName");
    }

    @Test
    void testOpenAddSongToPlaylistDialog() {
        navigationActivity.openAddSongToPlaylistDialog(new Song("id", "title", "url", 0, 0));
    }

    @Test
    void testConfirmEmailChange() {
        boolean result = navigationActivity.ConfirmEmailChange("newEmail");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testConfirmPasswordChange() {
        boolean result = navigationActivity.ConfirmPasswordChange("oldPassword", "newPassword");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testConfirmPhoneChange() {
        boolean result = navigationActivity.ConfirmPhoneChange("newNumber");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testConfirmCountryChange() {
        boolean result = navigationActivity.ConfirmCountryChange("newCountry");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testConfirmGenderChange() {
        boolean result = navigationActivity.ConfirmGenderChange("gender");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testConfirmDobChange() {
        boolean result = navigationActivity.ConfirmDobChange(0, 0, 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testApplyChange() {
        boolean result = navigationActivity.ApplyChange();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testLogOut() {
        navigationActivity.LogOut();
    }

    @Test
    void testOnBackPressed() {
        navigationActivity.onBackPressed();
    }
}