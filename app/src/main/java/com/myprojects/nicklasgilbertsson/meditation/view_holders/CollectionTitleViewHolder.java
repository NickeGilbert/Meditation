package com.myprojects.nicklasgilbertsson.meditation.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.myprojects.nicklasgilbertsson.meditation.R;

public class CollectionTitleViewHolder extends RecyclerView.ViewHolder  {

    View mView;

    public CollectionTitleViewHolder(View itemView)
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
        Button collection_title = (Button) mView.findViewById(R.id.collectionButtonRow);
        collection_title.setText(title);
        collection_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

    }

    private CollectionTitleViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(CollectionTitleViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
