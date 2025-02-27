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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**

 * @version 1
 * This class is responsible for the artist activity and account

 */

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
        /*setStatisticsData(18,472,1600
                ,45, 1321, 3845);*/

        getArtistStats();

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
    /**
     * This method is responsible for switching between home and library upon click
     */
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
    /**

     * This method is responsible for uploding audio files to Firebase Storage
     * @param audioUri The path to the audio in the device
     * acknowledgment to https://www.youtube.com/watch?v=XOf_v2f85RU

     */
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
    /**

     * This method is responsible for browsing audio files and selecting them from the device to upload them later

     */

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
    }

    public void Logout(){
        //Source: https://stackoverflow.com/questions/6609414/how-do-i-programmatically-restart-an-android-app
        Intent mStartActivity = new Intent(this, MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,
                mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
    /**
     * This method is responsible for choosing a photo from gallery and put it as profile picture
     */
    public void uploadPhoto (){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private void getArtistStats(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SONG_STATS;

        JsonObjectRequest  getRequest = new JsonObjectRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setStatisticsData(response.getInt("likes_day")
                                    , response.getInt("likes_month")
                                    , response.getInt("likes_year")
                                    , response.getInt("listeners_day")
                                    , response.getInt("listeners_month")
                                    , response.getInt("listeners_year"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ArtistHomeFragment()).commit();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArtistActivity.this, "An error occurred."
                                , Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        queue.add(getRequest);
    }
}
