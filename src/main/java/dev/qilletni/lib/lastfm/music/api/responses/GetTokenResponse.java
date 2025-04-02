package dev.qilletni.lib.lastfm.music.api.responses;

public record GetTokenResponse(Session session) {
    public record Session(String name, String key, int subscriber) {}
}
