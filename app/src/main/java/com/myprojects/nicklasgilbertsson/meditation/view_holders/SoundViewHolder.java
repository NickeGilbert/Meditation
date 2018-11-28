package com.myprojects.nicklasgilbertsson.meditation.view_holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public void setTitle(String title)
    {
        TextView sound_title = (TextView) mView.findViewById(R.id.sound_title);
        sound_title.setText(title);
    }

    private YogaViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(YogaViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
