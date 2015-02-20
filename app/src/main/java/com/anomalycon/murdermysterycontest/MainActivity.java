package com.anomalycon.murdermysterycontest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anomalycon.clues.ClueInterface;

import javax.inject.Inject;

public class MainActivity extends ActionBarActivity {
    @Inject
    ClueInterface cif;

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

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View promptView = layoutInflater.inflate(R.layout.activity_new_clue, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                final EditText input = (EditText) promptView.findViewById(R.id.cluePassword);

                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input use it to save the clue
                            }
                        })
                        .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
                System.out.println("New Clue Button Click"); //test
                Toast.makeText(getApplicationContext(), "New Clue Button Click", Toast.LENGTH_LONG).show(); //test
                //Intent intent = new Intent(v.getContext(), NewClueActivity.class);
                //startActivity(intent);
            }
        });

        //Viewing all clues
        final Button viewClueButton = (Button) findViewById(R.id.viewClueButton);
        viewClueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("View Clue Button Click"); //test
                Toast.makeText(getApplicationContext(), "View Clue Button Click", Toast.LENGTH_LONG).show(); //test
            }
        });

        //Making a guess
        final Button guessButton = (Button) findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Guess Button Click"); //test

                if(cif.countFoundClues()/cif.countAllClues() < 0.8)
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
