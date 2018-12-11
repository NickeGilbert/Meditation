package com.myprojects.nicklasgilbertsson.meditation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import static android.app.Service.START_STICKY;

public class SoundDetailActivity extends AppCompatActivity {

     TextView mDetailTitle;
     DatabaseReference mDatabase;
     private Button mButton;
     private MediaPlayer mMediaplayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private boolean playPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_spinner));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users-sound");
        mDatabase.keepSynced(true);
        mMediaplayer = new MediaPlayer();
        mMediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        String title = getIntent().getStringExtra("title");
        final String audioFile = getIntent().getStringExtra("song");

        mButton = (Button) findViewById(R.id.myMusicBtn);
        mDetailTitle = (TextView) findViewById(R.id.mySoundTitle);
        mDetailTitle.setText(title);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!playPause) {
                    mButton.setBackgroundResource(R.drawable.pause_button);

                    if (initialStage) {
                        new Player().execute(audioFile);
                    } else {
                        if (!mMediaplayer.isPlaying())
                            mMediaplayer.start();
                    }

                    playPause = true;

                } else {
                    mButton.setBackgroundResource(R.drawable.play_button);

                    if (mMediaplayer.isPlaying()) {
                        mMediaplayer.pause();
                    }

                    playPause = false;
                }
            }
        });


    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mMediaplayer.setDataSource(strings[0]);
                mMediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mButton.setBackgroundResource(R.drawable.play_button);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mMediaplayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }

            mMediaplayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Fetching your awesome tune...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaplayer != null) {
            mMediaplayer.stop();
        } else {

        }
    }
}
