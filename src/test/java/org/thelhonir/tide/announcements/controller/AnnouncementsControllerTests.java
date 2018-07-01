package org.thelhonir.tide.announcements.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.thelhonir.tide.announcements.exception.AnnouncementAlreadyExistsException;
import org.thelhonir.tide.announcements.exception.AnnouncementNotFoundException;
import org.thelhonir.tide.announcements.model.Announcement;
import org.thelhonir.tide.announcements.services.AnnouncementsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementsService announcementService;

    @Before
    public void createAnnouncementShouldReturnCreated() {
        String announcementId = "test1";
        Announcement expected = new Announcement.AnnouncementBuilder().withId(announcementId).build();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/create").content(announcementId))
                    .andExpect(status().is(HttpStatus.CREATED.value())).andExpect(content().json(toJson(expected)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void clearHazelcastMap() {
        this.announcementService.clearHazelcastInstance();
    }

    @Test
    public void createAnnouncementShouldThrowAnnouncementAlreadyExistsException() {
        String announcementId = "test1";
        AnnouncementAlreadyExistsException exception = new AnnouncementAlreadyExistsException();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/create").content(announcementId))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .andExpect(content().string(exception.getClass().getName() + ": " + exception.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void likeAnnouncementShouldReturnOk() {
        String announcementId = "test1";
        Announcement expected = new Announcement.AnnouncementBuilder().withId(announcementId).withLikes(1).build();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/like/" + announcementId))
                    .andExpect(status().is(HttpStatus.OK.value())).andExpect(content().json(toJson(expected)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void likeAnnouncementShouldThrowAnnouncementNotFoundException() {
        String announcementId = "nonExistingAnnouncement";
        AnnouncementNotFoundException exception = new AnnouncementNotFoundException();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/like/" + announcementId))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .andExpect(content().string(exception.getClass().getName() + ": " + exception.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dislikeAnnouncementShouldReturnOk() {
        String announcementId = "test1";
        Announcement expected = new Announcement.AnnouncementBuilder().withId(announcementId).withDislikes(1).build();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/dislike/" + announcementId))
                    .andExpect(status().is(HttpStatus.OK.value())).andExpect(content().json(toJson(expected)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dislikeAnnouncementShouldThrowAnnouncementNotFoundException() {
        String announcementId = "nonExistingAnnouncement";
        AnnouncementNotFoundException exception = new AnnouncementNotFoundException();

        try {
            this.mockMvc.perform(post("http://localhost:8080/announcements/dislike/" + announcementId))
                    .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .andExpect(content().string(exception.getClass().getName() + ": " + exception.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toJson(Announcement announcement) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(announcement);
    }
}
