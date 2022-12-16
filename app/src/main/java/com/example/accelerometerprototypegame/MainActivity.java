package com.example.accelerometerprototypegame;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button btnUp, btnDown, btnLeft, btnRight, btnSubmit, btnPlayAgain, btnGoHome;
    Vibrator v;
    TextView t1,t3,tvScoreDisplay, tvCountDown;
    EditText e1;
    String name;
    Boolean startSequence= false, gameStart=false;
    int timeCounter=4000, globalArrayCounter=4, counter=0, correctChoiceCounter=0, userHiscore=0;
    boolean isUp=false,isDown=false,isLeft=false,isRight=false;
    boolean positionChange, atBase;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    ArrayList<Integer> seriesOfNumbers = new ArrayList<Integer>();
    ArrayList<Integer> inputNumbers = new ArrayList<Integer>();
    DatabaseHandler db = new DatabaseHandler(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //For testing
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnPlayAgain=findViewById(R.id.btnPlayAgain);
        tvScoreDisplay=findViewById(R.id.tvCurrentScore);
        btnGoHome = findViewById(R.id.btnHome);
        tvCountDown = findViewById(R.id.tvCountDown);
        e1=findViewById(R.id.nameTv);
        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // choose the sensor you want
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       countDown();
    }
    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    public void creatingSequence()
    {
        gameStart=false;
        int random = new Random().nextInt(4);
        seriesOfNumbers.add(random);
        if(random ==0)
        {
            colorClick(btnLeft);
        }
        else if(random == 1)
        {
            colorClick(btnRight);
        }
        else if(random==2)
        {
            colorClick(btnDown);
        }
        else if(random==3)
        {
            colorClick(btnUp);
        }
    }
    public void checkAnswer(String input)
    {
        if(input =="Left")
        {
            inputNumbers.add(0);
        }
        else if(input == "Right")
        {
            inputNumbers.add(1);
        }
        else if(input == "Down")
        {
            inputNumbers.add(2);
        }
        else if(input == "Up")
        {
            inputNumbers.add(3);
        }
        counter++;
                if(counter==globalArrayCounter)
                {
                    for(int i = 0; i < globalArrayCounter; i++)
                    {
                        if(String.valueOf(seriesOfNumbers.get(i)) == String.valueOf(inputNumbers.get(i)))
                       {
                           correctChoiceCounter++;
                       }
                        else
                        {
                            Log.i("Name7", "Incorrect Choice");
                        }
                    }
                }
                if(correctChoiceCounter == globalArrayCounter)
                {
                    startSequence=false;
                    counter=0;
                    correctChoiceCounter=0;
                    globalArrayCounter++;
                    inputNumbers.clear();
                    seriesOfNumbers.clear();
                    userHiscore= userHiscore+10;
                    tvScoreDisplay.setText("Current Score: "+userHiscore);
                    Toast.makeText(this, "You Have Beaten the Level", Toast.LENGTH_SHORT).show();
                    onPause();
                    countDown();
                }
                else if(counter == globalArrayCounter)
                {
                    globalArrayCounter=4;
                    counter=0;
                    correctChoiceCounter=0;
                    inputNumbers.clear();
                    seriesOfNumbers.clear();
                    t1 = findViewById(R.id.tvGameOver);
                    t1.setVisibility(View.VISIBLE);
                    btnDown.setVisibility(View.GONE);
                    btnUp.setVisibility(View.GONE);
                    btnLeft.setVisibility(View.GONE);
                    btnRight.setVisibility(View.GONE);
                    t3=findViewById(R.id.tvHighscore);
                    t3.setVisibility(View.VISIBLE);
                    btnPlayAgain.setVisibility(View.VISIBLE);
                    tvScoreDisplay.setVisibility(View.GONE);
                    btnGoHome.setVisibility(View.VISIBLE);
                    ifGreaterThanFifthScore();
                    t3.setText("Your Score is "+ userHiscore);
                    startSequence=false;
                    onPause();
                }

    }
    public void playAgain(View view)
    {
        userHiscore=0;
        startSequence=false;
        t1.setVisibility(View.GONE);
        btnDown.setVisibility(View.VISIBLE);
        btnUp.setVisibility(View.VISIBLE);
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        e1.setVisibility(View.GONE);
        t3.setVisibility(View.GONE);
        btnPlayAgain.setVisibility(View.GONE);
        btnGoHome.setVisibility(View.GONE);
        tvScoreDisplay.setVisibility(View.VISIBLE);
        tvScoreDisplay.setText("Your Score is "+ userHiscore);
        countDown();
    }
    public void tiltSensor(SensorEvent event) {
        // called byt the system every x ms

        float x, y;
        x = Math.abs(event.values[0]); // get x value
        y = event.values[1];

        if(y < -2 && atBase)
        {
            colorClick(btnLeft);
            positionChange = true;
            checkAnswer("Left");
        }
        else if(y > 2 && atBase)
        {
            colorClick(btnRight);
            positionChange = true;
            checkAnswer("Right");
        }
        else if(x>9 && atBase)
        {
            colorClick(btnDown);
            positionChange = true;
            checkAnswer("Down");
        }
        else if(x<4 && atBase)
        {
            colorClick(btnUp);
            positionChange = true;
            checkAnswer("Up");
        }
        else if(x>=4 && x<=9 && y <=2 && y >=-2) //Checks if position is at base
        {
            positionChange = false;
            atBase = true;
        }

        if(positionChange && atBase)
        {
            v.vibrate(100);
            positionChange = false;
            atBase = false;
        }


    }

    public void countDown()
    {
        Toast.makeText(this, "Sequence started", Toast.LENGTH_SHORT).show();
        onPause();
        inputNumbers.clear();
        counter=0;
        int counterTime =4000;
        tvCountDown.setVisibility(View.VISIBLE);
        CountDownTimer cTimer2=null;
        cTimer2 = new CountDownTimer(counterTime,1000) {
            @Override
            public void onTick(long l) {
                startSequence=true;
                tvCountDown.setText(Integer.toString((int) (l/1000)));
            }
            @Override
            public void onFinish() {
                tvScoreDisplay.setText("Your Score is "+ userHiscore);
                tvCountDown.setVisibility(View.GONE);
                timerSequence();
            }
        };
        cTimer2.start();
    }
    public void timerSequence()
    {
        timeCounter = globalArrayCounter*1000;
        CountDownTimer cTimer = new CountDownTimer(timeCounter, 1000) {
            @Override
            public void onTick(long l) {
                creatingSequence();
            }
            @Override
            public void onFinish() {
                gameStart=true;
                Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                inputNumbers.clear();
                counter=0;
                timeCounter=0;
                timeCounter = globalArrayCounter*1000;
            }
        };
        cTimer.start();
        onResume();
    }

    public void changeScreen(View view)
    {
        Intent A2 = new Intent (view.getContext(), HighscoreActivity.class);
        name = String.valueOf(e1.getText());
        A2.putExtra("Name",name);
        A2.putExtra("Highscore", userHiscore);
        startActivity(A2);
        finish();
        userHiscore=0;
    }
    public void goHome(View view)
    {
        Intent A2 = new Intent (view.getContext(), homeActivity.class);
        startActivity(A2);
        finish();
    }
    void cancelTimer() {
        CountDownTimer cTimer = null;
        if(cTimer!=null)
            cTimer.cancel();
    }
    /*
     * Called by the system every x millisecs when sensor changes
     */
    public void onSensorChanged(SensorEvent event) {
        if(gameStart== true)
        {
             tiltSensor(event);
        }
    }
    public void ifGreaterThanFifthScore()
    {
        if(userHiscore >= db.top5Highscore().get(4).getHighscore())
        {
            e1.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }
    public void colorClick(Button btn)
    {

            btn.performClick();
            btn.setPressed(true);
            btn.invalidate();
            btn.setPressed(false);
            btn.invalidate();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
    }
}