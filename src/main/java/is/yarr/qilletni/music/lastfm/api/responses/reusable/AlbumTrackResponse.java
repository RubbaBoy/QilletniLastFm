package is.yarr.qilletni.music.lastfm.api.responses.reusable;

public record AlbumTrackResponse(
        StreamableResponse streamable,
        int duration,
        String url,
        String name,
        Attr attr,
        ArtistResponse artist
) {
    public record Attr(int rank) {}
}
