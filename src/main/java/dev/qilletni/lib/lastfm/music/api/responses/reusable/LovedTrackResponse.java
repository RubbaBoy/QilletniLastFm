package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import java.util.List;

public record LovedTrackResponse(
        SimpleArtistResponse artist,
        DateResponse date,
        String mbid,
        String url,
        String name,
        List<ImageResponse> image,
        StreamableResponse streamable
) {}
