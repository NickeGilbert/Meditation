package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CountdownActivity extends AppCompatActivity {

    private TextView countdownTimerText;
    private EditText minutes;
    Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        countdownTimerText = findViewById(R.id.countdownText);
        minutes = findViewById(R.id.enterMinutes);
        startTimer = findViewById(R.id.startTimer);
        resetTimer = findViewById(R.id.resetTimer);

        startTimer.setOnClickListener(new View.OnClickListener() {
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
                                minutes.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter no. of Minutes", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            stopCountdown();
                            startTimer.setText(getString(R.string.start_timer));
                        }
                }
            }
        });

        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopCountdown();
                startTimer.setText(getString(R.string.start_timer));
                countdownTimerText.setText(getString(R.string.timer));
            }
        });

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
                String hms = String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    v.vibrate(500);
                }
                startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }
}
