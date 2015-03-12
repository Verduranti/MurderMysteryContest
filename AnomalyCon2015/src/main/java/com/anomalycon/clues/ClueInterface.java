package com.anomalycon.clues;

import java.util.List;

/**
 * Here we have an interface to mask the implementation of Clues
 * TODO: Move all this server side
 *
 * Use example:
 * User saves a clue by entering the Key
 * Later, when making a guess or viewing the collected clues that Key is used to pull up Clue info
 */
public interface ClueInterface {

    /**
     * Get a list of all Clues in our record
     * @return the clue names
     */
    public List<String> getClueNames();

    /**
     * Try to retrieve a clue from our record
     * @param clueName the name of the clue to be retrieved (the password?)
     * @return the Clue
     */
    public Clue getClue(Key clueName);

    /**
     * Try to add the password for a clue to our record
     * @param clueName the password to be checked against
     * @return a status indicating what happened when the clue was added
     */
    public SaveClueStatus saveClue(Key clueName);

    public int countFoundClues();

    public int countAllClues();

}
