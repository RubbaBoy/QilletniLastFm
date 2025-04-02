package dev.qilletni.lib.lastfm.music.api;

import java.util.Map;

public record Page(int page, int count) {
    public Page() {
        this(-1, -1);
    }

    public void addToParams(Map<String, String> params) {
        if (page != -1) {
            params.put("page", String.valueOf(page));
        }

        if (count != -1) {
            params.put("limit", String.valueOf(count));
        }
    }
}
