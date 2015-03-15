package com.anomalycon.clues;

import com.anomalycon.murdermysterycontest.R;

/**
 * Created by alanbly on 3/14/15.
 */
public enum GuessStatus {
    SUBMITTED(true, R.string.guessSuccess),
    REJECTED(false, R.string.guessRejected),
    ERROR(false, R.string.guessFailed);

    boolean positive;
    int message;

    GuessStatus(boolean positive, int message) {
        this.positive = positive;
        this.message = message;
    }

    public boolean isPositive() {
        return positive;
    }

    public int getMessage() {
        return message;
    }
}
