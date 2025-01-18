package is.yarr.qilletni.music.lastfm.api.responses;

import com.google.gson.annotations.SerializedName;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.RecentTracksResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.UserResponseAttr;

public record GetRecentTracksResponse(RecentTracksResponse recenttracks) {}
