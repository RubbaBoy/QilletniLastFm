package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record WeeklyArtistChartResponse(
        String mbid,
        String url,
        String name,
        @SerializedName("@attr") RankAttrResponse attr,
        int playcount
) {
}
