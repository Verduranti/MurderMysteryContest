package com.anomalycon.murdermysterycontest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.anomalycon.clues.ClueInterface;

import javax.inject.Inject;

//This controls what displays when you find a new clue
public class NewClueActivity extends Activity {
    @Inject
    ClueInterface cif;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dagger things
        ContestApplication cApp = (ContestApplication) getApplication();
        cApp.getObjectGraph().inject(this);

        setContentView(R.layout.activity_new_clue);

        EditText editText = (EditText) findViewById(R.id.cluePassword);
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    if(!checkIfEmptyPassword()) {
//                        saveClue();
//                        handled = true;
//                    }
//                }
//                return handled;
//            }
//        });



    }

    public boolean checkIfEmptyPassword() {
        EditText editText = (EditText) findViewById(R.id.cluePassword);
        String password = editText.getText().toString();
        if(password.equals("")) {
            editText.setError("Oops! Cannot be empty!");
            return true;
        }
        return false;
    }

}

