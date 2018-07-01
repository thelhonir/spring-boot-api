package org.thelhonir.tide.announcements.exception;

public class AnnouncementAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 8342052402128891503L;
    private static final String errorMessage = "ANNOUNCEMENT ALREADY EXISTS";

    public AnnouncementAlreadyExistsException() {
        super(errorMessage);
    }

}
