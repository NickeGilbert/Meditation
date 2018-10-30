package com.myprojects.nicklasgilbertsson.meditation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

                Random generator = new Random();
                int number = generator.nextInt(8) + 1;

                Class activity = null;

                // Here, we are checking to see what the output of the random was
                switch(number) {
                    case 1:
                        activity = FirstActivity.class;
                        // We are adding the number of the activity to the list
                  //      activityList.remove(FirstActivity.class);
                        break;
                    case 2:
                        activity = SecondActivity.class;
                   //     activityList.remove(SecondActivity.class);
                        break;
                    case 3:
                        activity = ThirdActivity.class;
                    //    activityList.remove(ThirdActivity.class);
                        break;
                    case 4:
                        activity = FourthActivity.class;
                    //    activityList.remove(FourthActivity.class);
                        break;
                    case 5:
                        activity = FifthActivity.class;
                        break;
                    case 6:
                        activity = SixthActivity.class;
                        break;
                    case 7:
                        activity = SeventhActivity.class;
                        break;
                    default:
                        activity = EightActivity.class;
                      //  activityList.remove(FifthActivity.class);
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
