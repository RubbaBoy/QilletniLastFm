package is.yarr.qilletni.music.lastfm.api.responses;

import is.yarr.qilletni.music.lastfm.api.responses.reusable.AlbumChartResponse;

import java.util.List;

public record GetWeeklyAlbumChartResponse(WeeklyAlbumChart weeklyalbumchart) {
    public record WeeklyAlbumChart(List<AlbumChartResponse> album) {
    }
}
