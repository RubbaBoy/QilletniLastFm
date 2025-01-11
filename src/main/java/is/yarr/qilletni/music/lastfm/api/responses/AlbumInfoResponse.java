package is.yarr.qilletni.music.lastfm.api.responses;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.ImageResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.TagsResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.TracksResponse;

import java.util.List;

public record AlbumInfoResponse(AlbumResponse album) {
    public record AlbumResponse(
            String artist,
            String mbid,
            TagsResponse tags,
            String name,
            List<ImageResponse> image,
            TracksResponse tracks,
            String listeners,
            String playcount,
            String url
    ) {}
}
