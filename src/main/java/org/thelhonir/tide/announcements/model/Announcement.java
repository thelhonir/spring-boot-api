package org.thelhonir.tide.announcements.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Announcement implements Serializable {
    private static final long serialVersionUID = 6736077110147103463L;
    private final String id;
    private long likes;
    private long dislikes;

    public Announcement(String id) {
        this.id = id;
        this.likes = 0;
        this.dislikes = 0;
    }

    public String getId() {
        return this.id;
    }

    public long getLikes() {
        return likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void like() {
        this.likes++;
    }

    public void dislike() {
        this.dislikes++;
    }

}
