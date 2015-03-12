package com.anomalycon.clues;

/**
 * Created by verduranti on 2/20/15.
 */
public enum SaveClueStatus {

    SAVED(true), DUPLICATE(true), INVALID(false), BLANK(false);

    boolean haveClue;

    SaveClueStatus(boolean haveClue) {
        this.haveClue = haveClue;
    }

    public boolean hasClue() {
        return haveClue;
    }
}

