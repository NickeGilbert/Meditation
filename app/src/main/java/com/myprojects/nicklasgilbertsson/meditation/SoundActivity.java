package com.myprojects.nicklasgilbertsson.meditation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SoundActivity extends AppCompatActivity {

    private RecyclerView mSoundList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users-sound");
        mDatabase.keepSynced(true);

        mSoundList = (RecyclerView)findViewById(R.id.myRecyclerview);
        mSoundList.setHasFixedSize(true);
        mSoundList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Sounds, SoundViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Sounds, SoundViewHolder>
                (Sounds.class, R.layout.sound_row, SoundViewHolder.class, mDatabase) {

            @Override
            protected void populateViewHolder(SoundViewHolder viewHolder, Sounds model, int position) {
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


        /* Denna är med all sannolikhet inte rätt än!
        public void setSong(String song)
        {
            Button play_music = (Button) mView.findViewById(R.id.play_music_button);
            play_music.setText(song);
        }
        */
    }

}
