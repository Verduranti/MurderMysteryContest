package com.anomalycon.murdermysterycontest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.anomalycon.clues.ClueInterface;

import java.util.List;

import javax.inject.Inject;

/**
 * This class will display a list of clues
 * Created by verduranti on 2/22/15.
 */
public class AllClueActivity extends ActionBarActivity {
    @Inject
    ClueInterface cif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clues);

        //Dagger things
        ContestApplication cApp = (ContestApplication) getApplication();
        cApp.getObjectGraph().inject(this);

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
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //list.remove(item);
                                //adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });

    }
}
