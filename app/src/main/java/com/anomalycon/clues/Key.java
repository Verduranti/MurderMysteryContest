package com.anomalycon.clues;

/**
 * Created by verduranti on 2/10/15.
 */
public class Key {

    //Keys are all strings in this implementation
    private static String keyword;

    private Key() {

    }

    public Key(String keyword) {
        this.keyword = keyword;
    }

    public void setKey(String keyword) {
        this.keyword = keyword;
    }

    public String getKey() {
        return keyword;
    }
}
