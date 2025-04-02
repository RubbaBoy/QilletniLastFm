package dev.qilletni.lib.lastfm.music.api.responses;

import java.time.LocalDate;
import java.util.Optional;

public record DateRange(Optional<LocalDate> from, Optional<LocalDate> to) {

    public DateRange() {
        this(Optional.empty(), Optional.empty());
    }
    
    public DateRange(LocalDate from, LocalDate to) {
        this(Optional.ofNullable(from), Optional.ofNullable(to));
    }
    
    public static DateRange ofFrom(LocalDate from) {
        return new DateRange(from, null);
    }
    
    public static DateRange ofTo(LocalDate to) {
        return new DateRange(null, to);
    }
}
