package dev.qilletni.lib.lastfm.music.api.responses;

public record ErrorResponse(String message, int error) {
    public ErrorResponse {
        if (error < 0) {
            throw new IllegalArgumentException("Error code must be positive");
        }
    }
    
    public ErrorCode getErrorCode() {
        return ErrorCode.fromCode(error);
    }

    @Override
    public String toString() {
        return "(%d): %s".formatted(error, message);
    }
}
