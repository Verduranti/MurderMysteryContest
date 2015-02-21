package com.anomalycon.clues;

/* Here we have an interface to mask the implementation of Clues
 * TODO: Move all this server side
 *
 * Use example:
 * User saves a clue by entering the Key
 * Later, when making a guess or viewing the collected clues that Key is used to pull up Clue info
 */

public interface ClueInterface {



    public Clue getClue(Key clueName);

    public SaveClueStatus saveClue(Key clueName);

    public int countFoundClues();

    public int countAllClues();

}
