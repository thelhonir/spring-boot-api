package org.thelhonir.tide.announcements.exception;

public class AnnouncementNotFoundException extends Exception {

    private static final long serialVersionUID = 1689703681495571275L;

    public AnnouncementNotFoundException() {
        super("ANNOUNCEMENT NOT FOUND");
    }

}
