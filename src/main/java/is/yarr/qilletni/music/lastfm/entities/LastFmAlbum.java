package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Optional;

@Entity
public class LastFmAlbum implements Album {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmAlbum.class);

    @EmbeddedId
    private ArtistCompositeKey compositeKey;
    
    private LastFmArtist artist;

    // mbid not used for (unique) identification as it can be null/empty
    private String mbid;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<LastFmTrack> tracks;

    public LastFmAlbum() {}

    public LastFmAlbum(String mbid, String name, String artistName) {
        this.mbid = mbid == null ? "" : mbid;
        this.name = name;
        this.artist = new LastFmArtist("", "", artistName);
        
        this.compositeKey = new ArtistCompositeKey(name, artist);
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
        return name;
    }

    @Override
    public Artist getArtist() {
        LOGGER.error("Artists are not fully supported in the LastFm service provider. They will only have the artist name from an Album");
        return artist;
    }

    @Override
    public List<Artist> getArtists() {
        return List.of(getArtist());
    }

    public List<LastFmTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<LastFmTrack> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "LastFmAlbum{id='%s', name='%s', artist=%s, tracks=%s}".formatted(mbid, name, artist, tracks);
    }
}
