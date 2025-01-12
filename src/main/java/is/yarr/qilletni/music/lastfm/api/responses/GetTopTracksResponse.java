package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.TopTrackResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.UserResponseAttr;

import java.util.List;

public record GetTopTracksResponse(TopTracks toptracks) {
    public record TopTracks(
            List<TopTrackResponse> track,
            @SerializedName("@attr") UserResponseAttr attr
    ) {}
}
