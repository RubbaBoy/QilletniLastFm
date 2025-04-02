package dev.qilletni.lib.lastfm.music.api.responses;

import dev.qilletni.lib.lastfm.music.api.responses.reusable.TrackChartResponse;

import java.util.List;

public record GetWeeklyTrackChartResponse(WeeklyTrackChart weeklytrackchart) {
    public record WeeklyTrackChart(List<TrackChartResponse> track) {}
}
