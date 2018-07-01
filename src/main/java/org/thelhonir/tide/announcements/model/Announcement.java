package org.thelhonir.tide.announcements.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Announcement implements Serializable {
    private static final long serialVersionUID = 6736077110147103463L;
    private final String id;
    private long likes;
    private long dislikes;

    private Announcement(final String id, long likes, long dislikes) {
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
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

    // Created in a static context for testing purposes and deliver in time
    public static class AnnouncementBuilder {
        private String id;
        private long likes = 0;
        private long dislikes = 0;

        public AnnouncementBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public AnnouncementBuilder withLikes(long likes) {
            this.likes = likes;
            return this;
        }

        public AnnouncementBuilder withDislikes(long dislikes) {
            this.dislikes = dislikes;
            return this;
        }

        public Announcement build() {
            return new Announcement(id, likes, dislikes);
        }
    }
}
