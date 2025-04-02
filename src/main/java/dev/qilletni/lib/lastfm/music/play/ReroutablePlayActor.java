package dev.qilletni.lib.lastfm.music.play;

import dev.qilletni.api.music.ConsolePlayActor;
import dev.qilletni.api.music.PlayActor;
import dev.qilletni.api.music.Track;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ReroutablePlayActor implements PlayActor {

    private final static PlayActor defaultPlayActor = new ConsolePlayActor();
    private static Function<Track, CompletableFuture<PlayResult>> reroutedPlayTrack = defaultPlayActor::playTrack;

    @Override
    public CompletableFuture<PlayResult> playTrack(Track track) {
        return reroutedPlayTrack.apply(track);
    }

    public static void setReroutedPlayTrack(Function<Track, CompletableFuture<PlayResult>> playFunction) {
        reroutedPlayTrack = playFunction;
    }

    public static void resetReroutedPlayTrack() {
        reroutedPlayTrack = defaultPlayActor::playTrack;
    }

    public static PlayActor getDefaultPlay() {
        return defaultPlayActor;
    }
}
