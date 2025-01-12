package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record TrackChartResponse(
        ArtistChartResponse artist,
        List<ImageResponse> image,
        String mbid,
        String url,
        String name,
        @SerializedName("@attr") RankAttrResponse attr,
        int playcount
) {
    public record ArtistChartResponse(
            String mbid,
            @SerializedName("#text") String name
    ) {}
}
