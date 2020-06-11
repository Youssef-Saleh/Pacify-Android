package com.example.pacify;

import android.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class ArtistUploadFragmentTest {

    @InjectMocks
    ArtistUploadFragment artistUploadFragment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnCreateView() {
        View result = artistUploadFragment.onCreateView(null, null, null);
        Assertions.assertEquals(null, result);
    }
}