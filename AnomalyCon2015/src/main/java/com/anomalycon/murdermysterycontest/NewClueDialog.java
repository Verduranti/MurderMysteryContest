package com.anomalycon.murdermysterycontest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.anomalycon.clues.Clue;
import com.anomalycon.clues.SaveClueStatus;

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

        View view = inflater.inflate(R.layout.fragment_new_clue, null);

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
                saveClue(dialog);
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEND == actionId) {
            saveClue(getDialog());
            return true;
        }
        else {
            this.dismiss();
            return false;
        }
    }

    private SaveClueStatus saveClue(String password) {
        if(password.equals("")) {
            return SaveClueStatus.BLANK;
        }
        else {
            MainActivity activity = (MainActivity) getActivity();
            return activity.saveClue(password);
        }
    }

    private void setStatus(final SaveClueStatus status, Dialog dialog) {
        final FragmentActivity activity = getActivity();
        final Intent intent = new Intent(activity, ClueDetailActivity.class);
        intent.putExtra(Clue.CLUE_NAME_BUNDLE_ID, mEditText.getText().toString());
        if(status.hasClue()) {
            dialog.dismiss();
        }
        final String text = getResources().getString(status.getStatus());
        final Context applicationContext = activity.getApplicationContext();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (status.hasClue()) {
                    Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show();
                    activity.startActivity(intent);
                } else {
                    mEditText.setError(text);
                }
            }
        });
    }

    private void saveClue(final Dialog dialog) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final SaveClueStatus status = saveClue(mEditText.getText().toString());
                setStatus(status, dialog);
            }
        });
    }
}

