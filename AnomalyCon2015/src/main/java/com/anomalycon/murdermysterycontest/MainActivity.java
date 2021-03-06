package com.anomalycon.murdermysterycontest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.anomalycon.clues.Key;
import com.anomalycon.clues.SaveClueStatus;

public class MainActivity extends AnomalyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //Making a guess window.
        final Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Guess Button Click"); //test
                openGuessActivity();
            }
        });

    }

    private void openGuessActivity() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //This fraction sucks. Need 6/10? BAH. Might clean this up.
                System.out.println(cif.countFoundClues() + " / " + cif.countAllClues());
                if (cif.countFoundClues() / ((float) cif.countAllClues()) <= 0.5)
                {
                    toast(R.string.notEnoughClues);
                } else {
                    Intent myIntent = new Intent(MainActivity.this, SubmitGuessActivity.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    startActivity(myIntent);
                    //Replace this with form
                    toast(R.string.makeYourGuess);
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
        final Key key = new Key(password);
        return cif.saveClue(key);
    }

}
