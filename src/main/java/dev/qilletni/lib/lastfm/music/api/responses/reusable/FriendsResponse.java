package dev.qilletni.lib.lastfm.music.api.responses.reusable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record FriendsResponse(@SerializedName("@attr") UserResponseAttr attr, List<UserResponse> user) {
    
}
