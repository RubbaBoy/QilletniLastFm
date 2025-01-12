package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record AlbumChartResponse(
        ArtistChartResponse artist,
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
