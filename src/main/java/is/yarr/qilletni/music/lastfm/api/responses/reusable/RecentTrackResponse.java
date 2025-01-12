package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record RecentTrackResponse(
        RecentArtistResponse artist,
        String streamable,
        List<ImageResponse> image,
        String mbid,
        RecentAlbumResponse album,
        String name,
        @SerializedName("@attr") AttrResponse attr, // This is sometimes null?
        String url,
        DateResponse date // This is also sometimes null?
) {
    public record RecentAlbumResponse(
            String mbid,
            @SerializedName("#text") String text
    ) {}

    public record AttrResponse(
            @SerializedName("nowplaying") String nowPlaying
    ) {}

    public record RecentArtistResponse(
            String mbid,
            @SerializedName("#text") String text
    ) {}
}
