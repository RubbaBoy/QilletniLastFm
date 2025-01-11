package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import java.util.List;

@Entity
public class LastFmAlbum implements Album {
    
    @Id
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private LastFmArtist artist;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<LastFmTrack> tracks;

    public LastFmAlbum() {}

    public LastFmAlbum(String id, String name, LastFmArtist artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public List<Artist> getArtists() {
        return List.of(artist);
    }

    public List<LastFmTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<LastFmTrack> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "LastFmAlbum{id='%s', name='%s', artist=%s, tracks=%s}".formatted(id, name, artist, tracks);
    }
}
