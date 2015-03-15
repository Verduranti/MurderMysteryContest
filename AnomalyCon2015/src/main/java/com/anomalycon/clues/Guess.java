package com.anomalycon.clues;

import java.util.Collection;

/**
 * Created by alanbly on 3/14/15.
 */
public class Guess {
    private String name;
    private String email;
    private String guessText;
    private Collection<Clue> clues;

    public Guess() {
    }

    public Guess(String name, String email, String guessText) {
        this.name = name;
        this.email = email;
        this.guessText = guessText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuessText() {
        return guessText;
    }

    public void setGuessText(String guessText) {
        this.guessText = guessText;
    }

    public Collection<Clue> getClues() {
        return clues;
    }

    public void setClues(Collection<Clue> clues) {
        this.clues = clues;
    }
}
