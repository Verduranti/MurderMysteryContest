package com.anomalycon.murdermysterycontest;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.regex.Pattern;


public class EditTextValidator implements TextWatcher {

    private EditText text;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Setting custom drawable instead of red error indicator,
        error_indicator = getResources().getDrawable(R.drawable.emo_im_yelling);

        mUsername = (EditText) findViewById(R.id.etUsername);
        mPassword = (EditText) findViewById(R.id.etPassword);

        // Called when user type in EditText
        mUsername.addTextChangedListener(new InputValidator(mUsername));
        mPassword.addTextChangedListener(new InputValidator(mPassword));

        // Called when an action is performed on the EditText
        mUsername.setOnEditorActionListener(new EmptyTextListener(mUsername));
        mPassword.setOnEditorActionListener(new EmptyTextListener(mPassword));
    }
    */

        public EditTextValidator(EditText editText) {
            this.text = editText;
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        if (s.length() != 0) {
            switch (text.getId()) {
                case R.id.cluePassword: {
                    if (!Pattern.matches("^[a-z]{1,16}$", s)) {
                        text.setError("Oops! Username must have only a-z");
                    }
                }
                break;

//                    case R.id.etPassword: {
//                        if (!Pattern.matches("^[a-zA-Z]{1,16}$", s)) {
//                            text.setError("Oops! Password must have only a-z and A-Z");
//                        }
//                    }
//                    break;
            }
        }
    }
}