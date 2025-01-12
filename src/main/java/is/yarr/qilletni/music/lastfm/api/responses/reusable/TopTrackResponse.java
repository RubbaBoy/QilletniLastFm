package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record TopTrackResponse(
        StreamableResponse streamable,
        String mbid,
        String name,
        List<ImageResponse> image,
        TopArtistResponse artist,
        String url,
        int duration,
        @SerializedName("@attr") AttrResponse attr, // This is sometimes null?
        int playcount // This is also sometimes null?
) {
    public record AttrResponse(
            int rank
    ) {}

    public record TopArtistResponse(
            String mbid,
            String url,
            String name
    ) {}
}

