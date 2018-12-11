package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myprojects.nicklasgilbertsson.meditation.objects.Yoga;
import com.myprojects.nicklasgilbertsson.meditation.view_holders.YogaViewHolder;

import java.io.ByteArrayOutputStream;

public class YogaActivity extends AppCompatActivity {

    private static final String TAG = "YogaActivity";

    RecyclerView mRecyclerView;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    private ProgressBar mProgressbar;

    //This is the best one! https://www.youtube.com/watch?v=-ofYpirp4wA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        mProgressbar = (ProgressBar)findViewById(R.id.yogaProgressbar);
        mProgressbar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_spinner));
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Yoga");

    }

    @Override
    protected void onStart() {
        super.onStart();
        mProgressbar.setVisibility(View.VISIBLE);
        FirebaseRecyclerAdapter<Yoga, YogaViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Yoga, YogaViewHolder>(
                Yoga.class,
                R.layout.yoga_list_row,
                YogaViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(YogaViewHolder viewHolder, Yoga yoga, int position) {
                mProgressbar.setVisibility(View.GONE);
                viewHolder.setDetails(yoga.getTitle(), yoga.getImage(), yoga.getDesc());
            }

            @Override
            public YogaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                YogaViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                viewHolder.setOnClickListener(new YogaViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView mYogaTitle = view.findViewById(R.id.yoga_title);
                        ImageView mYogaImage = view.findViewById(R.id.yoga_images);
                        TextView mYogaDesc = view.findViewById(R.id.yoga_desc);

                        String mTitle = mYogaTitle.getText().toString();
                        String mDesc = mYogaDesc.getText().toString();
                        Drawable mDrawable = mYogaImage.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                        Intent intent = new Intent(view.getContext(), YogaDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("image", bytes);
                        intent.putExtra("title", mTitle);
                        intent.putExtra("desc", mDesc);
                        startActivity(intent);
                    }
                });
                return viewHolder;
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
















