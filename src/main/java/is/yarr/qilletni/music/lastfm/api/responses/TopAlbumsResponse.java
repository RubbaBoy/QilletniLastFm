package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.ArtistResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.ImageResponse;

import java.util.List;

public record TopAlbumsResponse(TopAlbums topalbums) {

    public record TopAlbums(
            List<Album> album
    ) {}

    public record Album(
            ArtistResponse artist,
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
