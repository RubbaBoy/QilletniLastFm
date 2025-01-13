package is.yarr.qilletni.music.lastfm.api.responses;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.SimpleArtistResponse;

// Not used yet! Removed the artist.getInfo API call for now
public record GetArtistInfoResponse(
        SimpleArtistResponse artist // There are more options given by the API, but we only need a couple due to limited support for artists
) {
}
