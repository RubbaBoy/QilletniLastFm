package is.yarr.qilletni.music.lastfm.entities;

import is.yarr.qilletni.api.music.Artist;

import javax.persistence.Embeddable;
import java.io.Serializable;

//@Entity
@Embeddable
public class LastFmArtist implements Artist, Serializable {

//    @EmbeddedId
//    private CompositeKey compositeKey;
    
    private String mbid = "";
    private String name;
    private String url;

    public LastFmArtist() {}

    public LastFmArtist(String mbid, String url, String name) {
        this.mbid = mbid == null ? "" : mbid;
        this.url = url;
        this.name = name;
        
//        this.compositeKey = new CompositeKey(name, mbid);
    }

//    public CompositeKey getCompositeKey() {
//        return compositeKey;
//    }

//    public Optional<String> getMbid() {
//        return Optional.ofNullable(mbid);
//    }

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
    public String toString() {
        return "LastFmArtist{id='%s', url='%s', name='%s'}".formatted(mbid, url, name);
    }

//    @Embeddable
//    public static class CompositeKey implements Serializable {
//
//        private String name;
//        private String mbid;
//
//        public CompositeKey() {}
//
//        public CompositeKey(String name, String mbid) {
//            this.name = name;
//            this.mbid = mbid;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getMbid() {
//            return mbid;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            LastFmArtist.CompositeKey that = (LastFmArtist.CompositeKey) o;
//            return name.equals(that.name) && mbid.equals(that.mbid);
//        }
//
//        @Override
//        public int hashCode() {
//            int result = name.hashCode();
//            result = 31 * result + mbid.hashCode();
//            return result;
//        }
//        
//        public String getId() {
//            return "%s-%s".formatted(name, mbid);
//        }
//    }
}
