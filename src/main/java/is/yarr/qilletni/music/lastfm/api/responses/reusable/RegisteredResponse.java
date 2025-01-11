package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record RegisteredResponse(String unixtime, @SerializedName("#text") String text) {}
