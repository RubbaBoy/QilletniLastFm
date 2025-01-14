package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class LastFmTrack implements Track {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmTrack.class);

    @EmbeddedId
    private ArtistCompositeKey compositeKey;
    
    // The mbid isn't always available for a track (who knows why)
    private String mbid = "";

    @Transient
    private LastFmArtist artist;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private LastFmAlbum album;
    
    private int duration;

    public LastFmTrack() {}

    public LastFmTrack(String mbid, String name, LastFmArtist artist, LastFmAlbum album, int duration) {
        this.mbid = mbid == null ? "" : mbid;
        this.artist = artist;
        this.album = album;
        this.duration = duration;

        this.compositeKey = new ArtistCompositeKey(name, artist.getName());
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
        LOGGER.error("Artists are not fully supported in the LastFm service provider. Expect inconsistent behavior.");
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
    public Album getAlbum() {
        return album;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LastFmTrack that = (LastFmTrack) object;
        return Objects.equals(mbid, that.mbid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid);
    }

    @Override
    public String toString() {
        return "LastFmTrack{id='%s', name='%s', artist=%s, album=%s, duration=%d}".formatted(mbid, compositeKey.getName(), artist, album, duration);
    }
}
