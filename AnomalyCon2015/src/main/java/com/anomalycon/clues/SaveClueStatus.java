package com.anomalycon.clues;

import com.anomalycon.murdermysterycontest.R;

/**
 * Created by verduranti on 2/20/15.
 */
public enum SaveClueStatus {

    SAVED(true, R.string.clueSaved),
    DUPLICATE(true, R.string.duplicateClueError),
    INVALID(false, R.string.badClueError),
    BLANK(false, R.string.blankError);

    boolean haveClue;
    int status;

    SaveClueStatus(boolean haveClue, int status) {
        this.haveClue = haveClue;
        this.status = status;
    }

    public boolean hasClue() {
        return haveClue;
    }

    public int getStatus() {
        return status;
    }
}

