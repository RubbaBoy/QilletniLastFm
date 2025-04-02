package dev.qilletni.lib.lastfm.music;

import dev.qilletni.api.lang.types.QilletniType;
import dev.qilletni.api.music.StringIdentifier;
import dev.qilletni.api.music.factories.AlbumTypeFactory;
import dev.qilletni.api.music.factories.CollectionTypeFactory;
import dev.qilletni.api.music.factories.SongTypeFactory;

import java.util.Optional;

public class LastFmStringIdentifier implements StringIdentifier {

    private final LastFmMusicCache musicCache;
    private final SongTypeFactory songTypeFactory;
    private final CollectionTypeFactory collectionTypeFactory;
    private final AlbumTypeFactory albumTypeFactory;

    public LastFmStringIdentifier(LastFmMusicCache musicCache, SongTypeFactory songTypeFactory, CollectionTypeFactory collectionTypeFactory, AlbumTypeFactory albumTypeFactory) {
        this.musicCache = musicCache;
        this.songTypeFactory = songTypeFactory;
        this.collectionTypeFactory = collectionTypeFactory;
        this.albumTypeFactory = albumTypeFactory;
    }

    @Override
    public Optional<QilletniType> parseString(String string) {
        return Optional.empty();
    }
}
