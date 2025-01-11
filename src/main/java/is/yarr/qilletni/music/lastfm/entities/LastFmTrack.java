package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.Track;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import java.util.List;
import java.util.Objects;

@Entity
public class LastFmTrack implements Track {

    @Id
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private LastFmArtist artist;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private LastFmAlbum album;
    
    private int duration;

    public LastFmTrack() {}

    public LastFmTrack(String id, String name, LastFmArtist artist, LastFmAlbum album, int duration) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LastFmTrack{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artist=" + artist +
                ", album=" + album +
                ", duration=" + duration +
                '}';
    }
}
