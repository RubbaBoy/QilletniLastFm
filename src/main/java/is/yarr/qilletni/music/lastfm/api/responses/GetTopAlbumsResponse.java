package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.SimpleArtistResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.ImageResponse;

import java.util.List;

public record GetTopAlbumsResponse(TopAlbums topalbums) {

    public record TopAlbums(
            List<Album> album
    ) {}

    public record Album(
            SimpleArtistResponse artist,
            List<ImageResponse> image,
            String mbid,
            String url,
            String playcount,
            @SerializedName("@attr") Attr attr,
            String name
    ) {
        public record Attr(int rank) {}
    }
}
