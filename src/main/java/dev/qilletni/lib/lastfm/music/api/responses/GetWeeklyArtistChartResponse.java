package dev.qilletni.lib.lastfm.music.api.responses;

import dev.qilletni.lib.lastfm.music.api.responses.reusable.WeeklyArtistChartResponse;

import java.util.List;

public record GetWeeklyArtistChartResponse(WeeklyArtistChart weeklyartistchart) {
    public record WeeklyArtistChart(List<WeeklyArtistChartResponse> artist) {
    }
}
