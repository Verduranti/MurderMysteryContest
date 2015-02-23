package com.anomalycon.murdermysterycontest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anomalycon.clues.ClueInterface;
import com.anomalycon.clues.Key;
import com.anomalycon.clues.SaveClueStatus;

import javax.inject.Inject;

public class MainActivity extends ActionBarActivity {
    @Inject
    ClueInterface cif;

    final Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dagger things
        ContestApplication cApp = (ContestApplication) getApplication();
        cApp.getObjectGraph().inject(this);

        //Adding new clues
        final Button newClueButton = (Button) findViewById(R.id.newClueButton);
        newClueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showNewClueDialog();
            }
        });

        //Viewing all clues
        final Button viewClueButton = (Button) findViewById(R.id.viewClueButton);
        viewClueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AllClueActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //Making a guess
        //Window.
        final Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Guess Button Click"); //test

                if(cif.countFoundClues()/cif.countAllClues() > 0.8) //switched to make testing easier
                {
                    Toast.makeText(getApplicationContext(), "Not enough clues. Keep hunting!", Toast.LENGTH_LONG).show();
                    System.out.println(Integer.toString(cif.countFoundClues())+" "+Integer.toString(cif.countAllClues()));
                }
                else
                {
                    Intent myIntent = new Intent(MainActivity.this, SubmitGuessActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    MainActivity.this.startActivity(myIntent);
                    //Replace this with form
                    Toast.makeText(getApplicationContext(), "You may guess now", Toast.LENGTH_LONG).show();
                }
            }
        });

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

    //Functions for saving new clues:
    public void showNewClueDialog() {
        FragmentManager fm = getSupportFragmentManager();
        NewClueDialog newClueDialog = new NewClueDialog();
        newClueDialog.show(fm, "fragment_new_clue");
    }

    public SaveClueStatus saveClue(String password) {
        Key key = new Key(password);
        SaveClueStatus status = cif.saveClue(key);
        if(status == SaveClueStatus.SAVED)
            Toast.makeText(getApplicationContext(), "Clue saved.", Toast.LENGTH_LONG).show();
        return status;
    }

}
