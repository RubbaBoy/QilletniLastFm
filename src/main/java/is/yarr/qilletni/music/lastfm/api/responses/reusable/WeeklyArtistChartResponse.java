package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.generic.GenericArtistResponse;

public record WeeklyArtistChartResponse(
        String mbid,
        String url,
        String name,
        @SerializedName("@attr") RankAttrResponse attr,
        int playcount
) implements GenericArtistResponse {
}
