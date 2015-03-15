package com.anomalycon.murdermysterycontest;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.anomalycon.clues.ClueInterface;

import javax.inject.Inject;

/**
 * Created by alanbly on 3/14/15.
 */
public abstract class AnomalyBaseActivity extends ActionBarActivity {
    @Inject
    protected ClueInterface cif;

    protected void toast(final int stringId) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), getString(stringId), Toast.LENGTH_LONG).show();
            }
        });
    }
}
