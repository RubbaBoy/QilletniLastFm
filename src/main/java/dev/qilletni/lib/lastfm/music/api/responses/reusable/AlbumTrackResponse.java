package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record AlbumTrackResponse(
        StreamableResponse streamable,
        int duration,
        String url,
        String name,
        @SerializedName("@attr") RankAttrResponse attr, // TODO: make sure this is @attr
        SimpleArtistResponse artist
) {
}
