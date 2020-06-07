package com.example.pacify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


@SuppressLint("Registered")
public class ArtistActivity extends AppCompatActivity {

    public String UserName;
    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = getIntent().getExtras();

            UserName = bundle.getString("username");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);



        BottomNavigationView bottomNav = findViewById(R.id.artist_nav_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ArtistHomeFragment()).commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ArtistHomeFragment())
                .commit();
    }







    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_artist_home:
                            selectedFragment = new ArtistHomeFragment();
                            break;
                        case R.id.nav_upload:
                            selectedFragment = new ArtistUploadFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


}
