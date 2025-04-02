package dev.qilletni.lib.lastfm.music.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ArtistCompositeKey implements Serializable {

    private String name;
    private String artistName;

    public ArtistCompositeKey() {}

    public ArtistCompositeKey(String name, String artistName) {
        this.name = name;
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistCompositeKey that = (ArtistCompositeKey) o;
        return name.equals(that.name) && artistName.equals(that.artistName);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + artistName.hashCode();
        return result;
    }

    public String getId() {
        return "%s-%s".formatted(name, artistName);
    }
}
