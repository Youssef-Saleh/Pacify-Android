package com.example.pacify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ArtistActivityTest {
    @InjectMocks
    ArtistActivity artistActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnRestart() {
        artistActivity.onRestart();
    }

    @Test
    void testOnCreate() {
        artistActivity.onCreate(null);
    }

    @Test
    void testOnStart() {
        artistActivity.onStart();
    }

    @Test
    void testSetStatisticsData() {
        artistActivity.setStatisticsData(2, 3, 4, 8, 10, 20);
    }

    @Test
    void testSelectAudio() {
        artistActivity.selectAudio();
    }

    @Test
    void testUploadPhoto() {
        artistActivity.uploadPhoto();
    }
}