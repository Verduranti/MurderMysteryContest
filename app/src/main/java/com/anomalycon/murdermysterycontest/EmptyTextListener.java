package com.anomalycon.murdermysterycontest;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EmptyTextListener implements OnEditorActionListener {

    private EditText text;

    //private Drawable error_indicator = android.R.drawable.ic_dialog_alert;

    public EmptyTextListener(EditText editText) {
        this.text = editText;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            // Called when user press Next button on the soft keyboard

            if (text.getText().toString().equals("")) {
                //text.setError("Oops! Password is empty!", error_indicator);
                text.setError("Oops! Cannot be empty!");
            }

        }
        return false;
    }

}
