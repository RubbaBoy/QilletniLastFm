package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Artist;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LastFmArtist implements Artist {
    
    @Id
    private String id;
    private String url;
    private String name;

    public LastFmArtist() {}

    public LastFmArtist(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LastFmArtist{id='%s', url='%s', name='%s'}".formatted(id, url, name);
    }
}
