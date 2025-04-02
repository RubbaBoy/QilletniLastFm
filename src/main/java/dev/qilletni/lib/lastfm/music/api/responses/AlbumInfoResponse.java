package dev.qilletni.lib.lastfm.music.api.responses;

import dev.qilletni.lib.lastfm.music.api.responses.reusable.ImageResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.TagsResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.TracksResponse;

import java.util.List;

public record AlbumInfoResponse(AlbumResponse album) {
    public record AlbumResponse(
            String artist,
            String mbid,
            TagsResponse tags,
            String name,
            List<ImageResponse> image,
            TracksResponse tracks,
            String listeners,
            String playcount,
            String url
    ) {}
}
