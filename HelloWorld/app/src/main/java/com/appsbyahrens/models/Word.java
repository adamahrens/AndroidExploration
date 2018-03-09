package com.appsbyahrens.models;

/**
 * Created by adamahrens on 3/9/18.
 */

public class Word {
    private String title;
    private Integer points;
    private Integer length;
    private String group;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!title.equals(word.getTitle())) return false;
        if (!points.equals(word.getPoints())) return false;
        if (!length.equals(word.getLength())) return false;
        return group.equals(word.getGroup());
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + points.hashCode();
        result = 31 * result + length.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }
}
