package com.anomalycon.murdermysterycontest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anomalycon.clues.ClueInterface;
import com.anomalycon.clues.Guess;
import com.anomalycon.clues.GuessStatus;

import javax.inject.Inject;

/**
 * This activity will get some user info and send that info to the AnomalyCon contest judges
 * Currently just uses a built in Email app. I'll clean that up later.
 * Created by verduranti on 2/22/15.
 */
public class SubmitGuessActivity extends AnomalyBaseActivity {
    private Button buttonSend;
    private EditText textName;
    private EditText textEmail;
    private EditText textGuessMurderer;
    private EditText textGuessWeapon;
    private EditText textGuessFormulae;

    private void submitGuess(final Guess guess) {
        final GuessStatus status = cif.makeGuess(guess);
        SubmitGuessActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (status.isPositive()) {
                    SubmitGuessActivity.this.finish();
                    toast(status.getMessage());
                } else {
                    textGuessMurderer.setError(getResources().getString(status.getMessage()));
                    textGuessWeapon.setError(getResources().getString(status.getMessage()));
                    textGuessFormulae.setError(getResources().getString(status.getMessage()));
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_guess);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textName = (EditText) findViewById(R.id.editTextName);
        textEmail = (EditText) findViewById(R.id.editTextEmail);
        textGuessMurderer = (EditText) findViewById(R.id.editTextGuessMurderer);
        textGuessWeapon = (EditText) findViewById(R.id.editTextGuessWeapon);
        textGuessFormulae = (EditText) findViewById(R.id.editTextGuessFormulae);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGuess();
            }
        });

        textGuessFormulae.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submitGuess();
                    return true;
                }
                return false;
            }
        });
    }

    private void submitGuess() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String name = textName.getText().toString();
                String email = textEmail.getText().toString();
                String guess = "Murderer: " + textGuessMurderer.getText().toString() +
                             "\nWeapon: " + textGuessWeapon.getText().toString() +
                             "\nFormulae: " + textGuessFormulae.getText().toString();

                submitGuess(new Guess(name, email, guess));
            }
        });
    }
}
