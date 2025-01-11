package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

public record DateResponse(
        String uts,
        @SerializedName("#text")
        String text
) {}
