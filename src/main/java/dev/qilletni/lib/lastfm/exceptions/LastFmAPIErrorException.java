package dev.qilletni.lib.lastfm.exceptions;

import dev.qilletni.api.exceptions.QilletniException;

public class LastFmAPIErrorException extends QilletniException {
    
    public LastFmAPIErrorException() {
        super();
    }

    public LastFmAPIErrorException(String message) {
        super(message);
    }

    public LastFmAPIErrorException(Throwable cause) {
        super(cause);
    }
}
