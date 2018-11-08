package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.EightActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.EleventhActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.FifthActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.FirstActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.FourteenActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.FourthActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.NineActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.SecondActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.SeventhActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.SixthActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.TenthActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.ThirdActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.ThirteenActivity;
import com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity.TwelfthActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = (Button) findViewById(R.id.MY_BUTTON);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We are creating a list, which will store the activities that haven't been opened yet
                ArrayList<Class> activityList = new ArrayList<>();
                activityList.add(FirstActivity.class);
                activityList.add(SecondActivity.class);
                activityList.add(ThirdActivity.class);
                activityList.add(FourthActivity.class);
                activityList.add(FifthActivity.class);
                activityList.add(SixthActivity.class);
                activityList.add(SeventhActivity.class);
                activityList.add(EightActivity.class);
                activityList.add(NineActivity.class);
                activityList.add(TenthActivity.class);
                activityList.add(EleventhActivity.class);
                activityList.add(TwelfthActivity.class);
                activityList.add(ThirteenActivity.class);
                activityList.add(FourteenActivity.class);

                Random generator = new Random();
                int number = generator.nextInt(14) + 1;

                Class activity = null;

                // Here, we are checking to see what the output of the random was
                switch(number) {
                    case 1:
                        activity = FirstActivity.class;
                        // We are adding the number of the activity to the list
                        activityList.remove(FirstActivity.class);
                        break;
                    case 2:
                        activity = SecondActivity.class;
                        activityList.remove(SecondActivity.class);
                        break;
                    case 3:
                        activity = ThirdActivity.class;
                        activityList.remove(ThirdActivity.class);
                        break;
                    case 4:
                        activity = FourthActivity.class;
                        activityList.remove(FourthActivity.class);
                        break;
                    case 5:
                        activity = FifthActivity.class;
                        activityList.remove(FifthActivity.class);
                        break;
                    case 6:
                        activity = SixthActivity.class;
                        activityList.remove(SixthActivity.class);
                        break;
                    case 7:
                        activity = SeventhActivity.class;
                        activityList.remove(SeventhActivity.class);
                        break;
                    case 8:
                        activity = EightActivity.class;
                        activityList.remove(EightActivity.class);
                        break;
                    case 9:
                        activity = NineActivity.class;
                        activityList.remove(NineActivity.class);
                        break;
                    case 10:
                        activity = TenthActivity.class;
                        activityList.remove(TenthActivity.class);
                        break;
                    case 11:
                        activity = EleventhActivity.class;
                        activityList.remove(EleventhActivity.class);
                        break;
                    case 12:
                        activity = TwelfthActivity.class;
                        activityList.remove(TwelfthActivity.class);
                        break;
                    case 13:
                        activity = ThirteenActivity.class;
                        activityList.remove(ThirteenActivity.class);
                    default:
                        activity = FourteenActivity.class;
                        activityList.remove(FourteenActivity.class);
                        break;
                }
                // We use intents to start activities
                Intent intent = new Intent(getBaseContext(), activity);
                // `intent.putExtra(...)` is used to pass on extra information to the next activity
                intent.putExtra("ACTIVITY_LIST", activityList);
                startActivity(intent);
            }
        });
    }
}
