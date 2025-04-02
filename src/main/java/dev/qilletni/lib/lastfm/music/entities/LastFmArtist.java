package dev.qilletni.lib.lastfm.music.entities;

import dev.qilletni.api.auth.ServiceProvider;
import dev.qilletni.api.music.Artist;
import dev.qilletni.lib.lastfm.LastFmServiceProvider;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Optional;

//@Entity
@Embeddable
public class LastFmArtist implements Artist, Serializable {

    private String mbid = "";
    private String name;
    private String url;

    public LastFmArtist() {}

    public LastFmArtist(String mbid, String url, String name) {
        this.mbid = mbid == null ? "" : mbid;
        this.url = url;
        this.name = name;
    }

    @Override
    public String getId() {
//        return compositeKey.getId();
        return "%s-%s".formatted(name, mbid);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @Transient
    public Optional<ServiceProvider> getServiceProvider() {
        return Optional.ofNullable(LastFmServiceProvider.getServiceProviderInstance());
    }

    @Override
    public String toString() {
        return "LastFmArtist{id='%s', url='%s', name='%s'}".formatted(mbid, url, name);
    }

}
