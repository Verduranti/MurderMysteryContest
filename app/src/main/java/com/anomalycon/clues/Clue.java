package com.anomalycon.clues;

/**
 * Created by verduranti on 2/10/15.
 */
public class Clue {

    private String name;
    private String storyText;

    private Clue() {
    }

    protected Clue(String name, String storyText) {
        this.name = name;
        this.storyText = storyText;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getStoryText() {
        return storyText;
    }

    public String getName() {
        return name;
    }

}
