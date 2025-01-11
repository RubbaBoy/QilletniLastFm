package is.yarr.qilletni.music.lastfm.api;

import java.util.Map;

public record Page(int page, int limit) {
    public Page() {
        this(-1, -1);
    }

    public void addToParams(Map<String, String> params) {
        if (page != -1) {
            params.put("page", String.valueOf(page));
        }

        if (limit != -1) {
            params.put("limit", String.valueOf(limit));
        }
    }
}
