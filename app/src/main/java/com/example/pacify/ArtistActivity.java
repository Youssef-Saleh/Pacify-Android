package com.example.pacify;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


@SuppressLint("Registered")
public class ArtistActivity extends AppCompatActivity {

    public String UserName;
    public int[] likes = new int[3];
    public int[] listeners = new int[3];
    FirebaseStorage storage;
    FirebaseDatabase database;
    Uri audioUri ;
    Uri selectedImage;
    ProgressDialog progressDialog;
    private static final int RESULT_LOAD_IMAGE = 1;

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
        storage = FirebaseStorage.getInstance("gs://pacify-1ce14.appspot.com");
        database = FirebaseDatabase.getInstance("gs://pacify-1ce14.appspot.com");

        //filling dummy data
        setStatisticsData(18,472,1600
                ,45, 1321, 3845);

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

    void setStatisticsData(int likesDay, int likesMonth, int likesYear
            , int lisDay, int lisMonth, int lisYear){
        likes[0] = likesDay;
        likes[1] = likesMonth;
        likes[2] = likesYear;
        listeners[0] = lisDay;
        listeners[1] = lisMonth;
        listeners[2] = lisYear;
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

    public void uploadFile(Uri audioUri) {
        progressDialog = new ProgressDialog(ArtistActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file ...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String fileName = System.currentTimeMillis()+ "";
        final StorageReference storageReference = storage.getReferenceFromUrl("gs://pacify-1ce14.appspot.com");
        storageReference.child("Uploads").child(fileName).putFile (audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                DatabaseReference reference= database.getReference();
                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ArtistActivity.this,"File Successfully uploaded",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(ArtistActivity.this,"File Not Successfully uploaded",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ArtistActivity.this,"File Not Successfully uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress =(int) (100 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            selectAudio();
        }else
            Toast.makeText(ArtistActivity.this,"Please provide permissoin",Toast.LENGTH_SHORT).show();
    }

    public void selectAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            audioUri = data.getData();
        }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
             selectedImage = data.getData ();
        }
        else
            Toast.makeText(ArtistActivity.this, "Please Choose a file", Toast.LENGTH_SHORT).show();
    }    public void Logout(){
        //Source: https://stackoverflow.com/questions/6609414/how-do-i-programmatically-restart-an-android-app
        Intent mStartActivity = new Intent(this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,
                mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    public void uploadPhoto (){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }
}
