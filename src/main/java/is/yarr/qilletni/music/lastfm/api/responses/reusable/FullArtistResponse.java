package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record FullArtistResponse(
        String streamable,
        List<ImageResponse> image,
        String mbid,
        String url,
        String playcount,
        @SerializedName("@attr") Attr attr,
        String name
) {
    public record Attr(int rank) {}
}
