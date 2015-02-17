package com.anomalycon.clues;

/**
 * Created by verduranti on 2/10/15.
 */
public interface ClueInterface {
    //I hate the name... sigh
    //This interface is supposed to hide the passwordwrapper (I hate that name too)
    //Basically, all this will be server side, but for now it is internal

    //Use case:
    //User saves com.anomalycon.clues, determined by "passwords"
    //Password is a look up to a Clue object
    //Clues have passwords, story text, location - com.anomalycon.clues are xml
    //User needs to be able to look at all com.anomalycon.clues they have discovered
    //User needs to not see com.anomalycon.clues they have not discovered
    //

    public Clue getClue(Key clueName);

    public void saveClue(Key clueName);

    public int countFoundClues();

    public int countAllClues();

}
