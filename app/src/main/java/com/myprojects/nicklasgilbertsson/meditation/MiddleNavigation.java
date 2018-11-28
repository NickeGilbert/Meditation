package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MiddleNavigation extends Fragment {

    private Button mCountdownButton, mYogaButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_middle, container, false);

        mCountdownButton = (Button)view.findViewById(R.id.countdown_button);
        mYogaButton = (Button)view.findViewById(R.id.yoga_button);

        mYogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YogaActivity.class);
                startActivity(intent);
            }
        });

        mCountdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CountdownActivity.class);
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
                .setActionBarTitle("Meditation");
    }
}
