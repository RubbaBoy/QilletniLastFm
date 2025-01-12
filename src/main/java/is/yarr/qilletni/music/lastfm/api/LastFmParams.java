package is.yarr.qilletni.music.lastfm.api;

import is.yarr.qilletni.music.lastfm.api.responses.DateRange;

import java.time.LocalDate;
import java.time.ZoneId;
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
    
    public LastFmParams setDateRange(DateRange dateRange) {
        dateRange.to().ifPresent(to -> params.put("to", String.valueOf(convertToEpochSeconds(to))));
        dateRange.from().ifPresent(from -> params.put("from", String.valueOf(convertToEpochSeconds(from))));
        
        return this;
    }

    public static long convertToEpochSeconds(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
    
    public Map<String, String> getMap() {
        return params;
    }
}
