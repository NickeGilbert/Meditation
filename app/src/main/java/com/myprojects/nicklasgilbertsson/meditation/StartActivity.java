package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.constraint.Constraints.TAG;

public class StartActivity extends Fragment {

    View view;

    private Button startMeditation, open_music_button, subscription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_start, container, false);

        startMeditation = view.findViewById(R.id.startMeditation);
        open_music_button = view.findViewById(R.id.soundButton);
        subscription = view.findViewById(R.id.subscription_button);

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
                Intent intent = new Intent(getActivity(), SoundActivity.class);
                startActivity(intent);
            }
        });

        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set title bar
        ((BottomNavigationActivity) getActivity())
                .setActionBarTitle("Home");
    }
}