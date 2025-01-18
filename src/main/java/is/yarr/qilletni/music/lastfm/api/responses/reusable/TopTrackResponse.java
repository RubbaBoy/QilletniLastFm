package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.generic.GenericArtistResponse;

import java.util.List;

public record TopTrackResponse(
        StreamableResponse streamable,
        String mbid,
        String name,
        List<ImageResponse> image,
        SimpleArtistResponse artist,
        String url,
        int duration,
        @SerializedName("@attr") AttrResponse attr, // This is sometimes null?
        Integer playcount // This is also sometimes null?
) {
    public record AttrResponse(
            int rank
    ) {}
}

