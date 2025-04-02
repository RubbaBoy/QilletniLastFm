package dev.qilletni.lib.lastfm.music.api.responses;

public enum ErrorCode {
    INVALID_SERVICE(2),
    INVALID_METHOD(3),
    AUTHENTICATION_FAILED(4),
    INVALID_FORMAT(5),
    INVALID_PARAMETERS(6),
    INVALID_RESOURCE(7),
    OPERATION_FAILED(8),
    INVALID_SESSION_KEY(9),
    INVALID_API_KEY(10),
    SERVICE_OFFLINE(11),
    INVALID_METHOD_SIGNATURE(13),
    TEMPORARY_ERROR(16),
    SUSPENDED_API_KEY(26),
    RATE_LIMIT_EXCEEDED(29);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}