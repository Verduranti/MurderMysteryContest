package com.anomalycon.clues;

/**
 * Implementation of Key. No interface at the moment because Key is so simple.
 */
public class Key  {

    //Keys are all strings in this implementation
    private String keyword;

    private Key() {

    }

    public Key(String keyword) {
        this.setKey(keyword);
    }

    public void setKey(String keyword) {
        this.keyword = keyword.toUpperCase();
    }

    public String getKey() {
        return keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (!keyword.equals(key.keyword)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

    @Override
    public String toString() {
        return "Key{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}
