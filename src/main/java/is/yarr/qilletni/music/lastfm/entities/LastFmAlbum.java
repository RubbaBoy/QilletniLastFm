package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.List;
import java.util.Optional;

@Entity
public class LastFmAlbum implements Album {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmAlbum.class);

    @EmbeddedId
    private ArtistCompositeKey compositeKey;

    @Transient
    private LastFmArtist artist;

    // mbid not used for (unique) identification as it can be null/empty
    private String mbid;

    public LastFmAlbum() {}

    public LastFmAlbum(String mbid, String name, String artistName) {
        this.mbid = mbid == null ? "" : mbid;
        this.artist = new LastFmArtist("", "", artistName);
        
        this.compositeKey = new ArtistCompositeKey(name, artistName);
    }

    @PostLoad
    private void onPostLoad() {
        if (artist == null && compositeKey != null) {
            artist = new LastFmArtist("", "", compositeKey.getArtistName());
        }
    }

    public ArtistCompositeKey getCompositeKey() {
        return compositeKey;
    }

    public Optional<String> getMbid() {
        return Optional.ofNullable(mbid);
    }

    @Override
    public String getId() {
        return compositeKey.getId();
    }

    @Override
    public String getName() {
        return compositeKey.getName();
    }

    @Override
    public Artist getArtist() {
        LOGGER.error("Artists are not fully supported in the LastFm service provider. They will only have the artist name from an Album");
        if (artist == null) {
            artist = new LastFmArtist("", "", compositeKey.getArtistName());
        }
        
        return artist;
    }

    @Override
    public List<Artist> getArtists() {
        return List.of(getArtist());
    }

    @Override
    public String toString() {
        return "LastFmAlbum{id='%s', name='%s', artist=%s}".formatted(mbid, compositeKey.getName(), artist);
    }
}
