package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record RegisteredResponse(String unixtime, @SerializedName("#text") String text) {}
