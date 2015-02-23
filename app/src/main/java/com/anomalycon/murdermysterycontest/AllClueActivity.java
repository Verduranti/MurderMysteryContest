package com.anomalycon.murdermysterycontest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.anomalycon.clues.ClueInterface;
import com.anomalycon.clues.Key;

import java.util.Set;

import javax.inject.Inject;

/**
 * This class will display a list of clues
 * Created by verduranti on 2/22/15.
 */
public class AllClueActivity extends Activity {
    @Inject
    ClueInterface cif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clues);


        final RelativeLayout rl=(RelativeLayout) findViewById(R.id.rl);
//        final RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
//                ((int)LayoutParams.WRAP_CONTENT,(int)LayoutParams.WRAP_CONTENT);
//        params.leftMargin=10;
//        params.topMargin=150;

        Set<Key> foundKeys = cif.getClueKeys();
        for (Key key : foundKeys) {

        }
    }
}
