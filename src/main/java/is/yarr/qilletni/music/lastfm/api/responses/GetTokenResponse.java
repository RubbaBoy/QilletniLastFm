package is.yarr.qilletni.music.lastfm.api.responses;

public record GetTokenResponse(Session session) {
    public record Session(String name, String key, int subscriber) {}
}
