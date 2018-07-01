package org.thelhonir.tide.announcements.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thelhonir.tide.announcements.exception.AnnouncementAlreadyExistsException;
import org.thelhonir.tide.announcements.exception.AnnouncementNotFoundException;
import org.thelhonir.tide.announcements.model.Announcement;
import org.thelhonir.tide.hazelcast.HazelcastConfiguration;

@Service
public class AnnouncementsService {

    @Autowired
    public static Config hazelConfig;

    private HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(hazelConfig);
    private Map<String, Announcement> announcementMap = this.hzInstance
            .getMap(HazelcastConfiguration.ANNOUNCEMENTS_MAP);

    /**
     * Lists in an asyncronous way the announcements created in the hazelcast
     * in-memory map
     * 
     * @return a completed future with the list of announcements
     */
    @Async
    public CompletableFuture<List<Announcement>> getAnnouncements() {
        return CompletableFuture.completedFuture(new ArrayList<Announcement>(this.announcementMap.values()));
    }

    /**
     * Creates in an asyncronous way a new announcement storing it in the hazelcast
     * in-memory map Checks if the id exists in the hazelcast in-memory map
     * 
     * @return a completed future with the created announcement
     * @throws AnnouncementAlreadyExistsException
     */
    @Async
    public CompletableFuture<Announcement> createAnnouncement(String id) throws AnnouncementAlreadyExistsException {
        if (!this.announcementExists(id)) {
            Announcement announcement = new Announcement(id);
            this.announcementMap.put(id, announcement);
            return CompletableFuture.completedFuture(announcement);
        } else {
            throw new AnnouncementAlreadyExistsException();
        }
    }

    /**
     * Updates in an asyncronous way an existing announcement in the hazelcast
     * in-memory map
     * 
     * @return a completed future with the updated announcement
     * @throws AnnouncementNotFoundException
     */
    @Async
    public CompletableFuture<Announcement> updateAnnouncement(Announcement announcement)
            throws AnnouncementNotFoundException {
        if (this.announcementExists(announcement.getId())) {
            this.announcementMap.put(announcement.getId(), announcement);
            return CompletableFuture.completedFuture(announcement);
        } else {
            throw new AnnouncementNotFoundException();
        }
    }

    /**
     * Gets in an asyncronous way an announcement by id from the hazelcast in-memory
     * map
     * 
     * @return a completed future with the retrieved announcement
     * @throws AnnouncementNotFoundException
     */
    @Async
    public CompletableFuture<Announcement> getAnnouncement(String id) throws AnnouncementNotFoundException {
        if (this.announcementExists(id)) {
            return CompletableFuture.completedFuture(this.announcementMap.get(id));
        } else {
            throw new AnnouncementNotFoundException();
        }
    }

    /**
     * Checks if the id exists in the hazelcast in-memory map
     */
    private Boolean announcementExists(String id) {
        return this.announcementMap.containsKey(id);
    }

}
