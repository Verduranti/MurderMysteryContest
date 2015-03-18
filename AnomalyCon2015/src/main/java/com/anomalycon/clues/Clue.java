package com.anomalycon.clues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/* Class controls what a Clue looks like
 *
 * Currently hold a string (the password that accesses it) and a longer string of "story text"
 *
 */
@JsonIgnoreProperties(value = "img")
public class Clue {

    public static final String CLUE_NAME_BUNDLE_ID = "CLUE_NAME";
    private String name;
    private String storyText;

    @JsonProperty("isFormula")
    private boolean formula;

    private Clue() {
    }

    protected Clue(String name, String storyText, boolean formula) {
        this.name = name;
        this.storyText = storyText;
        this.formula = formula;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public void setFormula(boolean formula) {
        this.formula = formula;
    }

    public String getStoryText() {
        return storyText;
    }

    public String getName() {
        return name;
    }

    public boolean isFormula() {
        return formula;
    }
}
