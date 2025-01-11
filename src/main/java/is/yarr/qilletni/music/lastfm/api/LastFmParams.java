package is.yarr.qilletni.music.lastfm.api;

import java.util.HashMap;
import java.util.Map;

public class LastFmParams {
    
    private final Map<String, String> params;

    public LastFmParams(Map<String, String> params) {
        this.params = new HashMap<>(params);
    }

    public LastFmParams setPage(Page page) {
        page.addToParams(params);
        return this;
    }
    
    public LastFmParams setPeriod(Period period) {
        if (period != Period.UNSET) {
            params.put("period", period.getPeriod());
        }
        
        return this;
    }
    
    public Map<String, String> getMap() {
        return params;
    }
}
