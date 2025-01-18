package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.RankAttrResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.SimpleArtistResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.ImageResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.UserResponseAttr;

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
