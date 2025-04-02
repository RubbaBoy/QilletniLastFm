package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import java.util.List;

public record FullTrackResponse(
        String name,
        String url,
        String mbid,
        long duration,
        StreamableResponse streamable,
        long listeners,
        long playcount,
        SimpleArtistResponse artist,
        AlbumResponse album,
        TagsResponse toptags
) {
    public record AlbumResponse(
            String artist,
            String title,
            String url,
            List<ImageResponse> image
    ) {}

}
