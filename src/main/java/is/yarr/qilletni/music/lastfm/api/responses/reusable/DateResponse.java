package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record DateResponse(
        long uts,
        @SerializedName("#text")
        String text
) {}
