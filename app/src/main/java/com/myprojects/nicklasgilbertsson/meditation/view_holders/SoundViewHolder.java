package com.myprojects.nicklasgilbertsson.meditation.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myprojects.nicklasgilbertsson.meditation.R;

public class SoundViewHolder extends RecyclerView.ViewHolder {

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

    public void setSong(String song) {
        Button mySong = (Button) mView.findViewById(R.id.play_music_button);
        mySong.setText(song);
    }











}
