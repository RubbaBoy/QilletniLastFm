package is.yarr.qilletni.music.lastfm.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record FriendsResponse(@SerializedName("@attr") Attr attr, List<UserResponse> user) {
    public record Attr(String user, String totalPages, String page, String perPage, String total) {}
}
