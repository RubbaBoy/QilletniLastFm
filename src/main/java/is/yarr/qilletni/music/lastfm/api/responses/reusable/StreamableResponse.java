package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record StreamableResponse(
        String fulltrack,
        @SerializedName("#text")
        String text
) {}
