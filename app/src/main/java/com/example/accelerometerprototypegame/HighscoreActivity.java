package com.example.accelerometerprototypegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    String nameIntent;
    int highscoreIntent;
    ListView listView;
    List<String> stringHighscore= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        listView= (ListView) findViewById(R.id.listView);

        String name = getIntent().getStringExtra("Name");
        int hiScore = getIntent().getIntExtra("Highscore",-1);
        String highscoreString;
        nameIntent=name;
        highscoreIntent=hiScore;

        DatabaseHandler db = new DatabaseHandler(this);

        name = nameIntent;
        highscoreString = String.valueOf(highscoreIntent);
        db.addHighscore(new Highscore(name, Integer.parseInt(highscoreString)));
        topFiveFilter();

        List<Highscore> highscoreList = db.top5Highscore();
        List<Highscore> highscore = db.getAllHighscore();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringHighscore);
        listView.setAdapter(arrayAdapter);



        Log.i("Insert: ", "Inserting ..");
        if(db.top5Highscore().size()==0)
        {
            db.addHighscore(new Highscore("Mary", 10));
            db.addHighscore(new Highscore("Jack", 20));
            db.addHighscore(new Highscore("Andrew", 30));
            db.addHighscore(new Highscore("John", 20));
            db.addHighscore(new Highscore("Anne", 0));
        }



        for (Highscore cn : highscore) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Highscore: " +
                    cn.getHighscore();

            Log.i("Name: ", log);
        }
        Log.i("divider", "====================");

        Highscore singleUser = db.getHighscoreClass(5);
        Log.i("highscore 5 is ", singleUser.getName());

        Log.i("divider", "====================");

        // Calling SQL statement
        int userCount = db.getHighscoreCount();
        Log.i("User count: ", String.valueOf(userCount));
    }
    public void topFiveFilter()
    {
       stringHighscore.clear();
        int counter=1;
        DatabaseHandler db = new DatabaseHandler(this);
        List<Highscore> highscoreList = db.top5Highscore();
        for (Highscore cn2 : highscoreList) {

            String log = counter +". "+ " Name: " + cn2.getName() + ", "+ "  Highscore: " +
                    cn2.getHighscore();
            stringHighscore.add(log);
            Log.i("Name10: ", log);
            counter++;
        }
    }
    public void btnReturn(View view)
    {
        finish();
    }
//    public void addHighScore(View view) {
//        String name;
//
//        String highscore;
//
//        name = nameIntent;
//        highscore = String.valueOf(highscoreIntent);
//        DatabaseHandler db = new DatabaseHandler(this);
//        db.addHighscore(new Highscore(name, Integer.parseInt(highscore)));
//        int userCount = db.getHighscoreCount();
//        Log.i("User count: ", String.valueOf(userCount));
//
//        List<Highscore> highscoreList = db.getAllHighscore();
//
//
//        for (Highscore cn2 : highscoreList) {
//
//
//            String log = "Id: " + cn2.getID() + " ,Name: " + cn2.getName() + " ,Highscore: " +
//                    cn2.getHighscore();
//            //if(cn2.getHighscore() >= )
//            Log.i("Name9: ", log);
//        }
//        topFiveFilter();
//    }

}