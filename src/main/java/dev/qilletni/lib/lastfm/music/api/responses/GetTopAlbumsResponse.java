package dev.qilletni.lib.lastfm.music.api.responses;

import com.google.gson.annotations.SerializedName;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.RankAttrResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.SimpleArtistResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.ImageResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.UserResponseAttr;

import java.util.List;

public record GetTopAlbumsResponse(TopAlbums topalbums) {

    public record TopAlbums(
            List<Album> album,
            @SerializedName("@attr") UserResponseAttr attr
    ) {}

    public record Album(
            SimpleArtistResponse artist,
            List<ImageResponse> image,
            String mbid,
            String url,
            Integer playcount,
            @SerializedName("@attr") RankAttrResponse attr,
            String name
    ) {}
}
