package com.anomalycon.murdermysterycontest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity will get some user info and send that info to the AnomalyCon contest judges
 * Currently just uses a built in Email app. I'll clean that up later.
 * Created by verduranti on 2/22/15.
 */
public class SubmitGuessActivity extends Activity {

    Button buttonSend;
    TextView textTo;
    TextView textSubject;
    EditText textMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_guess);

        buttonSend = (Button) findViewById(R.id.buttonSend);
        textTo = (TextView) findViewById(R.id.editTextTo);
        textSubject = (TextView) findViewById(R.id.editTextSubject);
        textMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String to = textTo.getText().toString();
                String subject = textSubject.getText().toString();
                String message = textMessage.getText().toString();

                final Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                AlertDialog.Builder builder = new AlertDialog.Builder(SubmitGuessActivity.this);
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                email.setType("message/rfc822");
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }
                        }
                ); builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((MainActivity)getActivity()).doNegativeClick();
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.setTitle(getString(R.string.emailAppWarningTitle));
                alert.setMessage(getString(R.string.emailAppWarning));
                alert.show();

            }
        });
    }
}
