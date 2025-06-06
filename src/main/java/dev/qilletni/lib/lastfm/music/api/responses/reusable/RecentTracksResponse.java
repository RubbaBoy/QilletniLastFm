package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record RecentTracksResponse(List<RecentTrackResponse> track, @SerializedName("@attr") UserResponseAttr attr) {}
