package com.myprojects.nicklasgilbertsson.meditation.MantraExercisesActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.myprojects.nicklasgilbertsson.meditation.BottomNavigationActivity;
import com.myprojects.nicklasgilbertsson.meditation.MainActivity;
import com.myprojects.nicklasgilbertsson.meditation.R;

import java.util.ArrayList;
import java.util.Random;

public class EleventhActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleventh);
        final Button myButton = (Button) findViewById(R.id.button5);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Class> activityList = new ArrayList<>();
                Bundle extras = getIntent().getExtras();
                activityList = (ArrayList<Class>) extras.get("ACTIVITY_LIST");

                if(activityList.size() == 0) {
                    // Do something when after all activities have been opened
                    //     doSomeAction();

                    myButton.setOnClickListener(new View.OnClickListener()
                    {    public void onClick(View v)
                    {
                        Intent intent = new Intent(EleventhActivity.this, BottomNavigationActivity.class);
                        EleventhActivity.this.startActivity(intent);
                    }
                    });

                    Toast.makeText(getApplicationContext(), "Finished! Tap again to restart", Toast.LENGTH_LONG).show();
                } else {
                    // Now, the random number is generated between 1 and however many
                    // activities we have remaining
                    Random generator = new Random();
                    int number = generator.nextInt(activityList.size()) + 1;

                    Class activity = null;

                    // Here, we are checking to see what the output of the random was
                    switch(number) {
                        case 1:
                            // We will open the first remaining activity of the list
                            activity = activityList.get(0);
                            // We will now remove that activity from the list
                            activityList.remove(0);
                            break;
                        case 2:
                            // We will open the second remaining activity of the list
                            activity = activityList.get(1);
                            activityList.remove(1);
                            break;
                        case 3:
                            // We will open the third remaining activity of the list
                            activity = activityList.get(2);
                            activityList.remove(2);
                            break;
                        case 4:
                            // We will open the fourth remaining activity of the list
                            activity = activityList.get(3);
                            activityList.remove(3);
                            break;
                        case 5:
                            activity = activityList.get(4);
                            activityList.remove(4);
                            break;
                        case 6:
                            activity = activityList.get(5);
                            activityList.remove(5);
                            break;
                        case 7:
                            activity = activityList.get(6);
                            activityList.remove(6);
                            break;
                        case 8:
                            activity = activityList.get(7);
                            activityList.remove(7);
                            break;
                        case 9:
                            activity = activityList.get(8);
                            activityList.remove(8);
                            break;
                        case 10:
                            activity = activityList.get(9);
                            activityList.remove(9);
                            break;
                        case 11:
                            activity = activityList.get(10);
                            activityList.remove(10);
                            break;
                        case 12:
                            activity = activityList.get(11);
                            activityList.remove(11);
                            break;
                        case 13:
                            activity = activityList.get(12);
                            activityList.remove(12);
                            break;
                        default:
                            // We will open the fifth remaining activity of the list
                            activity = activityList.get(13);
                            activityList.remove(13);
                            break;
                    }

                    // Note: in the above, we might not have 3 remaining activities, for example,
                    // but it doesn't matter because that case wouldn't be called anyway,
                    // as we have already decided that the number would be between 1 and the number of
                    // activities left.


                    // Starting the activity, and passing on the remaining number of activities
                    // to the next one that is opened
                    Intent intent = new Intent(getBaseContext(), activity);
                    intent.putExtra("ACTIVITY_LIST", activityList);
                    startActivity(intent);
                }
            }
        });
    }
}
