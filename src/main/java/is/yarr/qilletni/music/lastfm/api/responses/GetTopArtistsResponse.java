package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.FullArtistResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.UserResponseAttr;

import java.util.List;

public record GetTopArtistsResponse(TopArtists topartists) {
    public record TopArtists(
            List<FullArtistResponse> artist,
            @SerializedName("@attr") UserResponseAttr attr
    ) {}
}
