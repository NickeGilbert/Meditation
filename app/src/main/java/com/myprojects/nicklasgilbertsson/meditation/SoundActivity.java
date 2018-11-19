package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.myprojects.nicklasgilbertsson.meditation.Objects.Sounds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private RecyclerView mSoundList;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private Button mButton;

    private MediaPlayer mMediaplayer;

    //https://medium.com/@ssaurel/implement-audio-streaming-in-android-applications-8758d3bc62f1
    //https://www.youtube.com/watch?v=GV7wQkacwIc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users-sound");
        mDatabase.keepSynced(true);

        mButton = (Button)findViewById(R.id.play_music_button);
        mSoundList = (RecyclerView)findViewById(R.id.myRecyclerview);
        progressBar = (ProgressBar)findViewById(R.id.myProgressbar);
        mSoundList.setHasFixedSize(true);
        mSoundList.setLayoutManager(new LinearLayoutManager(this));


        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchAudioUrlFromFirebase();

        /*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseRecyclerAdapter<Sounds, SoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Sounds, SoundViewHolder>
                (Sounds.class, R.layout.sound_row, SoundViewHolder.class, mDatabase) {

            @Override
            protected void populateViewHolder(SoundViewHolder viewHolder, Sounds model, int position) {
                progressBar.setVisibility(View.GONE);
                viewHolder.setTitle(model.getTitle());
               // viewHolder.setSong(model.getSong()); Ska säkerligen läggas in här sen!
            }
        };

        mSoundList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public SoundViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title)
        {
            TextView sound_title = (TextView) mView.findViewById(R.id.sound_title);
            sound_title.setText(title);
        }
    }



    //Denna koden startar specifikt denna musiken när sidan startar!
    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/ibeacon-b9caa.appspot.com/o/music%2Ffranz-liszt-liebestraum-3.mp3?alt=media&token=d7cb094d-6014-4b2b-897b-c70b728dba86");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    final String url = uri.toString();
                    mMediaplayer.setDataSource(url);
                    // wait for media player to get prepare
                    mMediaplayer.setOnPreparedListener(SoundActivity.this);
                    mMediaplayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", e.getMessage());
                    }
                });

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
    /*                              */


















    public class StorageExternaloFirebase {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        Context dContext;

        public StorageExternaloFirebase(Context context) {
            this.dContext = context;
        }

      /*  static StorageExternaloFirebase sharedInstance(Context context){
            return new StorageExternaloFirebase(context);
        }*/

        public void createFile(byte[] bytes, String minne) throws IOException {
            File directory = new File(dContext.getExternalFilesDir(
                    Environment.DIRECTORY_MUSIC), "latar");
            File file = new File(directory, minne);
            if (!directory.mkdirs()) {
                Log.e("APe", "Directory not created");
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file.getPath());
            fos.write(bytes);
            Log.d("APe", "sparaIExternal: David " + fos);
            fos.close();
            Log.d("DAVID", "createFile: " + file.isFile());
        }

        public File fetchFilePath(String minne){
            File directory = new File(dContext.getExternalFilesDir(
                    Environment.DIRECTORY_MUSIC), "latar");
            File file = new File(directory, minne);
            return file;
        }

        public void downloadFromFirebase(String latFB, final String minne){
            final long TEN_MEGABYTE = 1024 * 1024 * 12;
            Log.d("APA", "downloadFromFirebase: " + latFB);
            reference.child(latFB).getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    try {
                        createFile(bytes, minne);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("APA", "onSuccess: " + e);
                    }
                }
            });
        }
    }

}
