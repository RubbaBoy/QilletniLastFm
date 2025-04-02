package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record DateResponse(
        long uts,
        @SerializedName("#text")
        String text
) {}
