package dev.qilletni.lib.lastfm.music.api.responses;

import com.google.gson.annotations.SerializedName;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.TopTrackResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.UserResponseAttr;

import java.util.List;

public record GetTopTracksResponse(TopTracks toptracks) {
    public record TopTracks(
            List<TopTrackResponse> track,
            @SerializedName("@attr") UserResponseAttr attr
    ) {}
}
