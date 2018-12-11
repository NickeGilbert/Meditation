package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myprojects.nicklasgilbertsson.meditation.objects.CollectionRow;
import com.myprojects.nicklasgilbertsson.meditation.view_holders.CollectionTitleViewHolder;
import static android.content.ContentValues.TAG;

public class StartActivity extends Fragment {

    View view;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private Button startMeditation, open_music_button, subscription;
    private String collectionValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_start, container, false);

        mRecyclerView = view.findViewById(R.id.myRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        startMeditation = view.findViewById(R.id.startMeditation);
        open_music_button = view.findViewById(R.id.soundButton);
        subscription = view.findViewById(R.id.subscription_button);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users-sound");

        Log.d(TAG, "onCreateView: " + collectionValue);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange"+postSnapshot.getKey());
                    collectionValue = postSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        open_music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), SoundActivity.class);
                startActivity(intent);
            }
        });

        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
              //  startActivity(intent);
                Toast.makeText(getActivity(), "Currently unavailable. More amazing content will be added!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CollectionRow, CollectionTitleViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CollectionRow, CollectionTitleViewHolder>
                (CollectionRow.class, R.layout.button_row, CollectionTitleViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(final CollectionTitleViewHolder viewHolder, final CollectionRow model, int position) {
                Log.d(TAG, "populateViewHolder: " + collectionValue);

                viewHolder.setTitle(collectionValue);
                viewHolder.setOnClickListener(new CollectionTitleViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        mRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Intent intent = new Intent(getContext(), SoundActivity.class);
                                intent.putExtra("collectionValue", collectionValue);
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
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set title bar
        ((BottomNavigationActivity) getActivity())
                .setActionBarTitle("Meditation");
    }
}
