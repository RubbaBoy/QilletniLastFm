package is.yarr.qilletni.music.lastfm.api.responses;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.TrackChartResponse;

import java.util.List;

public record GetWeeklyTrackChartResponse(WeeklyTrackChart weeklytrackchart) {
    public record WeeklyTrackChart(List<TrackChartResponse> track) {}
}
