package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import dev.qilletni.lib.lastfm.music.api.responses.reusable.generic.GenericArtistResponse;

public record SimpleArtistResponse(
        String url,
        String name,
        String mbid
) implements GenericArtistResponse {}
