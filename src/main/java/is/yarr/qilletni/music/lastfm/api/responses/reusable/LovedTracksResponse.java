package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record LovedTracksResponse(List<LovedTrackResponse> track, @SerializedName("@attr") UserResponseAttr attr) {}
