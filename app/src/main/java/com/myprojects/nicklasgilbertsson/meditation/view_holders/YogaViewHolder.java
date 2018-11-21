package com.myprojects.nicklasgilbertsson.meditation.view_holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myprojects.nicklasgilbertsson.meditation.R;
import com.squareup.picasso.Picasso;

public class YogaViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public YogaViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public void setDetails(String title, String myImage, String desc) {
        TextView mTitle = mView.findViewById(R.id.yoga_title);
        ImageView mImage = mView.findViewById(R.id.yoga_images);
        TextView mDesc = mView.findViewById(R.id.yoga_desc);

        mTitle.setText(title);
        mDesc.setText(desc);
        Picasso.get().load(myImage).into(mImage);
    }

    private YogaViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(YogaViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
















