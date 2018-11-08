package com.myprojects.nicklasgilbertsson.meditation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SoundActivity extends AppCompatActivity {

    private RecyclerView mSoundList;
    private DatabaseReference mDatabase;

    // Hur jag får firebase data i cardview https://www.youtube.com/watch?v=dth3aq_QPzU
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

}
