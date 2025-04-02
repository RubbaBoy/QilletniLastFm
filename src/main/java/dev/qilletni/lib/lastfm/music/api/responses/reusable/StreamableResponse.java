package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record StreamableResponse(
        String fulltrack,
        @SerializedName("#text")
        String text
) {}
