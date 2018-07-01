package org.thelhonir.tide.announcements.exception;

public class AnnouncementAlreadyExistsException extends Exception {
    
    private static final long serialVersionUID = 8342052402128891503L;

    public AnnouncementAlreadyExistsException() {
        super("ANNOUNCEMENT ALREADY EXISTS");
    }
}
