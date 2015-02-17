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

    //unnecessary oops
//    @Override
//    public boolean equals(Object object) {
//        boolean result = false;
//        if (object == null || object.getClass() != getClass()) {
//            result = false;
//        } else {
//            Key key = (Key) object;
//            if (this.keyword == getKey()) {
//                result = true;
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public int hashCode(Object object) {
//        return 1;
//    }



}
