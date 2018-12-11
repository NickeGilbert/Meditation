package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.myprojects.nicklasgilbertsson.meditation.objects.Sounds;
import com.myprojects.nicklasgilbertsson.meditation.objects.User;
import com.myprojects.nicklasgilbertsson.meditation.view_holders.SoundViewHolder;
import com.myprojects.nicklasgilbertsson.meditation.view_holders.YogaViewHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundActivity extends AppCompatActivity {

    private RecyclerView mSoundList;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;

    private static final String TAG = "SoundActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        String collectionTitle = getIntent().getStringExtra("collectionValue");
       // Log.d(TAG, "onCreate: " + collectionTitle);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users-sound").child(collectionTitle);
        Log.d(TAG, "onCreate: " + mDatabase);
        mDatabase.keepSynced(true);

        mSoundList = (RecyclerView)findViewById(R.id.myRecyclerview);
        progressBar = (ProgressBar)findViewById(R.id.myProgressbar);
        progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_spinner));
        mSoundList.setHasFixedSize(true);
        mSoundList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        FirebaseRecyclerAdapter<Sounds, SoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Sounds, SoundViewHolder>
                (Sounds.class, R.layout.sound_row, SoundViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(final SoundViewHolder viewHolder, final Sounds model, int position) {
                progressBar.setVisibility(View.GONE);

                final String audio_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());


                viewHolder.setOnClickListener(new SoundViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        TextView mSoundTitle = view.findViewById(R.id.sound_title);
                        final String mTitle = mSoundTitle.getText().toString();

                        mDatabase.child(audio_key).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String audioFile = (String) dataSnapshot.child("song").getValue();
                               // String audioTitle = mTitle;

                                Intent intent = new Intent(getApplicationContext(), SoundDetailActivity.class);
                               // intent.putExtra("title", audioTitle);
                                intent.putExtra("song", audioFile);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        mSoundList.setAdapter(firebaseRecyclerAdapter);
    }
}