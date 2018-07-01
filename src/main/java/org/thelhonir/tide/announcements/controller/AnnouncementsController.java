package org.thelhonir.tide.announcements.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thelhonir.tide.announcements.exception.AnnouncementAlreadyExistsException;
import org.thelhonir.tide.announcements.exception.AnnouncementNotFoundException;
import org.thelhonir.tide.announcements.model.Announcement;
import org.thelhonir.tide.announcements.services.AnnouncementsService;

@RestController
@RequestMapping(path = "/announcements")
public class AnnouncementsController {

    @Autowired
    private AnnouncementsService announcementsService;

    @RequestMapping(value = "/get-announcements", method = RequestMethod.GET)
    public ResponseEntity<?> getAnnouncements() {
        try {
            return new ResponseEntity<List<Announcement>>(this.announcementsService.getAnnouncements().get(),
                    HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAnnouncement(@RequestBody String id) {
        try {
            if (Objects.nonNull(id)) {
                Announcement announcement = this.announcementsService.createAnnouncement(id).get();
                return new ResponseEntity<Announcement>(announcement, HttpStatus.CREATED);
            } else {
                throw new NullPointerException();
            }
        } catch (AnnouncementAlreadyExistsException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NullPointerException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> likeAnnouncement(@PathVariable("id") String id) {
        try {
            if (Objects.nonNull(id)) {
                Announcement announcement = this.announcementsService.getAnnouncement(id).get();
                announcement.like();
                Announcement responseAnnouncement = this.announcementsService.updateAnnouncement(announcement).get();
                return new ResponseEntity<Announcement>(responseAnnouncement, HttpStatus.OK);
            } else {
                throw new NullPointerException();
            }
        } catch (AnnouncementNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NullPointerException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/dislike/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> dislikeAnnouncement(@PathVariable("id") String id) {
        try {
            if (Objects.nonNull(id)) {
                Announcement announcement = this.announcementsService.getAnnouncement(id).get();
                announcement.dislike();
                Announcement responseAnnouncement = this.announcementsService.updateAnnouncement(announcement).get();
                return new ResponseEntity<Announcement>(responseAnnouncement, HttpStatus.OK);
            } else {
                throw new NullPointerException();
            }
        } catch (AnnouncementNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NullPointerException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
