package is.yarr.qilletni.music.lastfm.api;

public enum Period {
    UNSET(""),
    OVERALL("overall"),
    SEVEN_DAY("7day"),
    ONE_MONTH("1month"),
    THREE_MONTH("3month"),
    SIX_MONTH("6month"),
    TWELVE_MONTH("12month");

    private final String period;

    Period(String period) {
        this.period = period;
    }
    
    public static Period fromString(String period) {
        for (var p : Period.values()) {
            if (p.getPeriod().equals(period)) {
                return p;
            }
        }
        
        return UNSET;
    }

    public String getPeriod() {
        return period;
    }
}
