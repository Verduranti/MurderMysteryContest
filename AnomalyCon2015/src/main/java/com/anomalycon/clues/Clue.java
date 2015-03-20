package com.anomalycon.clues;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonProperty("formula")
    private String formulaName;
    private String flavorText;

    @JsonProperty("isFormula")
    private boolean formula;

    public Clue() {
    }

    public Clue(String name, String storyText, String formulaName, String flavorText, boolean formula) {
        this.name = name;
        this.storyText = storyText;
        this.formulaName = formulaName;
        this.flavorText = flavorText;
        this.formula = formula;
    }

    public Clue(String name, String storyText) {
        this.name = name;
        this.storyText = storyText;
        this.formulaName = "";
        this.flavorText = "";
        this.formula = false;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getName() {
        return name;
    }

    public String getStoryText() {
        return storyText;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public String getFlavorText() {
        return flavorText;
    }

    @JsonIgnore
    public String getFormulaText() {
        return getFormulaName()+"\n\n"+ flavorText+"\n\n- Dr. Shniffenpot";
    }

    public boolean isFormula() {
        return formula;
    }
}
