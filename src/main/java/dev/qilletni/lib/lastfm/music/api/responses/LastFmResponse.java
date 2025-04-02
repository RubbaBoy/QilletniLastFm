package dev.qilletni.lib.lastfm.music.api.responses;

public class LastFmResponse<T> {
    
    private final boolean error;
    private ErrorResponse errorResponse;
    private T response;
    
     public LastFmResponse(T response) {
         this.error = false;
         this.response = response;
     }
     
    public LastFmResponse(ErrorResponse errorResponse) {
        this.error = true;
        this.errorResponse = errorResponse;
    }

    public boolean isError() {
        return error;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public T getResponse() {
        return response;
    }
}
