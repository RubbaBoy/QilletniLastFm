package dev.qilletni.lib.lastfm.music.api.responses;

import dev.qilletni.lib.lastfm.music.api.responses.reusable.AlbumChartResponse;

import java.util.List;

public record GetWeeklyAlbumChartResponse(WeeklyAlbumChart weeklyalbumchart) {
    public record WeeklyAlbumChart(List<AlbumChartResponse> album) {
    }
}
