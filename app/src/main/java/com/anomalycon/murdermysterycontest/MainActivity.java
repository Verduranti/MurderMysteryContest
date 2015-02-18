package com.anomalycon.murdermysterycontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anomalycon.clues.ClueInterface;

import javax.inject.Inject;


public class MainActivity extends ActionBarActivity {
    @Inject
    ClueInterface cif;
    //intents need primatives. not a good way to pass around classes
    //using Dagger instead, hopefully
    //public final static String EXTRA_MESSAGE = "com.anomalycon.murdermysteryapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dagger things
        ContestApplication cApp = (ContestApplication) getApplication();
        cApp.getObjectGraph().inject(this);

        setContentView(R.layout.activity_main);

        final Button newClueButton = (Button) findViewById(R.id.newClueButton);
        newClueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("New Clue Button Click"); //test
                Toast.makeText(getApplicationContext(), "New Clue Button Click", Toast.LENGTH_LONG).show(); //test
                Intent intent = new Intent(v.getContext(), NewClueActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        final Button viewClueButton = (Button) findViewById(R.id.viewClueButton);
        viewClueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("View Clue Button Click"); //test
                Toast.makeText(getApplicationContext(), "View Clue Button Click", Toast.LENGTH_LONG).show(); //test
            }
        });

        final Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Guess Button Click"); //test

                if(cif == null){
                    //Add some better error handling here.
                    Toast.makeText(getApplicationContext(), "Still broken", Toast.LENGTH_LONG).show();

                }
                else if(cif.countFoundClues()/cif.countAllClues() < 0.8)
                {
                    Toast.makeText(getApplicationContext(), "Not enough clues. Keep hunting!", Toast.LENGTH_LONG).show();
                    System.out.println(Integer.toString(cif.countFoundClues())+" "+Integer.toString(cif.countAllClues()));
                }
                else
                {
                    //Replace this with form
                    Toast.makeText(getApplicationContext(), "You may guess now", Toast.LENGTH_LONG).show();
                }
            }
        });


        //This might not be the best place for this
        //Also - delete this when the server-side stuff goes in
        //Initialize the Clues:
//        ClueManager clues = ClueManager.getInstance();
//        clues.fillInClues(this);
        //End stuff to be deleted
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
