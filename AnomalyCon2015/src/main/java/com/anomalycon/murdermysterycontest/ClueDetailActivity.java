package com.anomalycon.murdermysterycontest;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anomalycon.clues.Clue;
import com.anomalycon.clues.ClueImageView;
import com.anomalycon.clues.Key;


public class ClueDetailActivity extends AnomalyBaseActivity {
    private TextView textClueName;
    private TextView textClueText;
    private EditText editClueNotes;
    private ProgressBar loadingProgress;
    private ClueImageView clueImageView;
    private Clue clue;

    private void handleClueSelect(final String clueName) {
        final Key key = new Key(clueName);
        clue = cif.getClue(key);

        clueImageView.setVisibility(View.GONE);

        textClueName.setText(clue.getName());
        editClueNotes.setText(clue.getStoryText());

        if(clue.isFormula()) {
            setTitle(clue.getFormulaName());
            textClueText.setVisibility(View.VISIBLE);
            textClueText.setText(clue.getFormulaText());
            loadingProgress.setVisibility(View.VISIBLE);
            //clueImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.stat_notify_sync));//ic_popup_sync
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Drawable imageForClue = cif.getImageForClue(key);
                        ClueDetailActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                clueImageView.setImageDrawable(imageForClue);
                                loadingProgress.setVisibility(View.GONE);
                                clueImageView.setVisibility(View.VISIBLE);
                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            textClueText.setVisibility(View.GONE);
            setTitle(clue.getName());
        }
    }

    private void saveClue() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                clue.setStoryText(editClueNotes.getText().toString());
                cif.saveClue(clue);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue_detail);

        textClueName = (TextView) findViewById(R.id.ClueTitle);
        textClueText = (TextView) findViewById(R.id.ClueText);
        editClueNotes = (EditText) findViewById(R.id.ClueNotes);
        loadingProgress = (ProgressBar) findViewById(R.id.Progress);
        clueImageView = (ClueImageView) findViewById(R.id.ClueImageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String clueName = extras.getString("CLUE_NAME");
            handleClueSelect(clueName);
        }

        editClueNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveClue();
            }
        });
    }
}
