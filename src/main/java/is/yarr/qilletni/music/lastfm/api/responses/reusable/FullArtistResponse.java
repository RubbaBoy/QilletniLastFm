package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.generic.GenericArtistResponse;

import java.util.List;

public record FullArtistResponse(
        String streamable,
        List<ImageResponse> image,
        String mbid,
        String url,
        Integer playcount,
        @SerializedName("@attr") Attr attr,
        String name
) implements GenericArtistResponse {
    public record Attr(int rank) {}
}
