package com.anomalycon.clues;

import android.content.Context;
import android.content.res.Resources;

import com.anomalycon.murdermysterycontest.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Wrapper class designed to guard access to the user's discovered com.anomalycon.clues.
 */
public class ClueManager implements ClueInterface {

    //private static ClueManager myInstance = null;

    private Map<Key, Clue> foundClueMap;

    private Map<Key, Clue> allClueMap;

    //private static Context myContext;

    private ClueManager() {
        //not used
    }

    public ClueManager(Context context) {
        foundClueMap = new HashMap<>();
        allClueMap = new HashMap<>();
        fillInClues(context);
    }

//    public static ClueManager getInstance(){
//        if(myInstance == null)
//        {
//            myInstance = new ClueManager();
//        }
//        return myInstance;
//    }

    protected void fillInClues(Context context) {
        Resources res = context.getResources();
        String[] clues = res.getStringArray(R.array.clue_array);
        //Fill in the allClueMap

        for(String str : clues)
        {
            String[] nameValuePair = str.split("\\|");
            Clue newClue = new Clue(nameValuePair[0], nameValuePair[1]); //a bit hacktastic
            Key newKey = new Key(nameValuePair[0]);
            allClueMap.put(newKey, newClue);
        }
    }

    @Override
    public Clue getClue(Key clueName) {
        if(foundClueMap.containsKey(clueName))
        {
            return foundClueMap.get(clueName);
        }
        else
        {
            //random comment
            return null;
        }
    }

    @Override
    public SaveClueStatus saveClue(Key clueName) {
        boolean returnStatus = false;
        if(foundClueMap.containsKey(clueName)) {
            return SaveClueStatus.DUPLICATE;
        }
        else if(allClueMap.containsKey(clueName)) {
            foundClueMap.put(clueName, allClueMap.get(clueName));
            return SaveClueStatus.SAVED;
        }
        else {
            //Some kind of error handling for bad clue names
            return SaveClueStatus.INVALID;
        }
    }

    @Override
    public int countFoundClues() {
        return foundClueMap.size();
    }

    @Override
    public int countAllClues() {
        return allClueMap.size();
    }

}
