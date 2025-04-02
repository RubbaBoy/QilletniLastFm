package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record ImageResponse(
        String size,
        @SerializedName("#text")
        String text
) {}
