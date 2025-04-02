package dev.qilletni.lib.lastfm.music.api.responses;

import com.google.gson.annotations.SerializedName;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.FullArtistResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.UserResponseAttr;

import java.util.List;

public record GetTopArtistsResponse(TopArtists topartists) {
    public record TopArtists(
            List<FullArtistResponse> artist,
            @SerializedName("@attr") UserResponseAttr attr
    ) {}
}
