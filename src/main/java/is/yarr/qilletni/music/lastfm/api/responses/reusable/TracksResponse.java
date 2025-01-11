package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import java.util.List;

public record TracksResponse(
        List<AlbumTrackResponse> track
) {}
