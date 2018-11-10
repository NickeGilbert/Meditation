package com.myprojects.nicklasgilbertsson.meditation;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class CountdowmFragment extends Fragment implements View.OnClickListener {

    private static TextView countdownTimerText;
    private static EditText minutes;
    private static Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countdowm, container, false);

        countdownTimerText = (TextView) view.findViewById(R.id.countdownText);
        minutes = (EditText) view.findViewById(R.id.enterMinutes);
        startTimer = (Button) view.findViewById(R.id.startTimer);
        resetTimer = (Button) view.findViewById(R.id.resetTimer);

        setListeners();


        return view;
    }

    private void setListeners() {
        startTimer.setOnClickListener(this);
        resetTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTimer:
                if (countDownTimer == null)  {
                    String getMinutes = minutes.getText().toString();

                    if(!getMinutes.equals("") && getMinutes.length() > 0) {
                        int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;

                        startTimer(noOfMinutes);
                        startTimer.setText(getString(R.string.stop_timer));
                    } else {
                        Toast.makeText(getActivity(), "Please enter no. of Minutes", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    stopCountdown();
                    startTimer.setText(getString(R.string.start_timer));
                }
                break;

            case R.id.resetTimer:
                stopCountdown();
                startTimer.setText(getString(R.string.start_timer));
                countdownTimerText.setText(getString(R.string.timer));
                break;
        }
    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    @Override
    public void onResume() {
        super.onResume();

        // Set title bar
        ((BottomNavigationActivity) getActivity())
                .setActionBarTitle("Meditation");
    }
}
