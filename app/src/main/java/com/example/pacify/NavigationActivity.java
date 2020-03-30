package com.example.pacify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
            playerNav.animate().alpha(1f).setDuration(1000);
            fab.setImageResource(android.R.drawable.stat_sys_download);
        }
        else{
            playerNav.setVisibility(View.INVISIBLE);
            playerNav.animate().alpha(0f).setDuration(1);
            fab.setImageResource(android.R.drawable.stat_sys_upload);


        }
    }
    public void test(View view){
        Toast.makeText(NavigationActivity.this,"Clicked",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
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
