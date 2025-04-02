package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import java.util.List;

public record UserResponse(String name, String url, String country, String playlists, String playcount, List<ImageResponse> image, RegisteredResponse registered, String realname, String subscriber, String bootstrap, String type) {}
