package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import java.util.List;

public record TracksResponse(
        List<AlbumTrackResponse> track
) {}
