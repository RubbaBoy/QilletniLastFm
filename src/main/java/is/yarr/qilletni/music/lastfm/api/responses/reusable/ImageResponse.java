package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record ImageResponse(
        String size,
        @SerializedName("#text")
        String text
) {}
