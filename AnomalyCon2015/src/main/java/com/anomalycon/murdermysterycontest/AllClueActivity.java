package com.anomalycon.murdermysterycontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anomalycon.clues.Clue;
import com.anomalycon.clues.ClueInterface;
import com.anomalycon.clues.Key;

import java.util.List;

import javax.inject.Inject;

/**
 * This class will display a list of clues
 * Created by verduranti on 2/22/15.
 */
public class AllClueActivity extends AnomalyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clues);

        List<String> foundKeyNames = cif.getClueNames();

        final ListView listview = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<String> adapter = new ArrayAdapter<> (getBaseContext(),
                android.R.layout.simple_list_item_1, foundKeyNames);

        listview.setAdapter(adapter);

        listview.setEmptyView(findViewById(R.id.noClues));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String clueName = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(AllClueActivity.this, ClueDetailActivity.class);
                intent.putExtra(Clue.CLUE_NAME_BUNDLE_ID, clueName);
                AllClueActivity.this.startActivity(intent);
            }

        });

    }
}
