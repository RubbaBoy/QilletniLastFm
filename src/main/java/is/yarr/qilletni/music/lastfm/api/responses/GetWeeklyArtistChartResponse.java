package is.yarr.qilletni.music.lastfm.api.responses;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.WeeklyArtistChartResponse;

import java.util.List;

public record GetWeeklyArtistChartResponse(WeeklyArtistChart weeklyartistchart) {
    public record WeeklyArtistChart(List<WeeklyArtistChartResponse> artist) {
    }
}
