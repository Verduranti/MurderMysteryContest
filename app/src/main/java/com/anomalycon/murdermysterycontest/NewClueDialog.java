package com.anomalycon.murdermysterycontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class NewClueDialog extends DialogFragment implements OnEditorActionListener {

    private EditText mEditText;

    public NewClueDialog() {
        // Empty constructor required for DialogFragment
    }

    public interface NewClueDialogListener {
        void onFinishEditDialog(String inputText);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view = inflater.inflate(R.layout.activity_new_clue, null);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Should probably clean this up
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //((MainActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();

        mEditText = (EditText) view.findViewById(R.id.cluePassword);

        mEditText.setOnEditorActionListener(this);

        mEditText.requestFocus();
        dialog.getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog)getDialog();

        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {
                PasswordStatus status = saveClue(mEditText.getText().toString());
                switch (status) {
                    case OK:
                        dialog.dismiss();
                        //add intent to open up extra content
                        return;
                    case BLANK:
                        mEditText.setError(getResources().getString(R.string.blankError));
                        break;
                    case DUPLICATE:
                        mEditText.setError(getResources().getString(R.string.duplicateClueError));
                        break;
                    default: // NOT_FOUND, ERROR
                        mEditText.setError(getResources().getString(R.string.badClueError));
                        break;
                }
                return;
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEND == actionId) {
            PasswordStatus status = saveClue(mEditText.getText().toString());
            switch (status) {
                case OK:
                    this.dismiss();
                    //Add intent to open up extra content
                    break;
                case BLANK:
                    mEditText.setError(getResources().getString(R.string.blankError));
                    break;
                case DUPLICATE:
                    mEditText.setError(getResources().getString(R.string.duplicateClueError));
                    break;
                default: // NOT_FOUND, ERROR
                    mEditText.setError(getResources().getString(R.string.badClueError));
                    break;
            }
            return true;
        }

        else {
            this.dismiss();
            return false;
        }
    }

    private enum PasswordStatus {
        BLANK, NOT_FOUND, OK, DUPLICATE, ERROR
    }

    public PasswordStatus saveClue(String password) {
        if(password.equals("")) {
            return PasswordStatus.BLANK;
        }
        else {
            MainActivity activity = (MainActivity) getActivity();
            switch (activity.saveClue(password))
            {
                case SAVED:
                    return PasswordStatus.OK;
                case DUPLICATE:
                    return PasswordStatus.DUPLICATE;
                case INVALID:
                    return PasswordStatus.NOT_FOUND;
                default:
                    return PasswordStatus.ERROR; //Shouldn't be possible as written right now
            }
        }
    }
}

