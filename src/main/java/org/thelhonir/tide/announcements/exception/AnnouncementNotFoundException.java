package org.thelhonir.tide.announcements.exception;

public class AnnouncementNotFoundException extends Exception {

    private static final long serialVersionUID = 1689703681495571275L;
    private static final String errorMessage = "ANNOUNCEMENT NOT FOUND";

    public AnnouncementNotFoundException() {
        super(errorMessage);
    }

}
