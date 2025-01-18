package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.generic.GenericArtistResponse;

public record SimpleArtistResponse(
        String url,
        String name,
        String mbid
) implements GenericArtistResponse {}
