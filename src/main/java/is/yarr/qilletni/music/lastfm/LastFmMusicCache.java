package is.yarr.qilletni.music.lastfm;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.MusicCache;
import is.yarr.qilletni.api.music.MusicFetcher;
import is.yarr.qilletni.api.music.Playlist;
import is.yarr.qilletni.api.music.Track;
import is.yarr.qilletni.lib.lastfm.database.EntityTransaction;
import is.yarr.qilletni.music.lastfm.entities.ArtistCompositeKey;
import is.yarr.qilletni.music.lastfm.entities.LastFmAlbum;
import is.yarr.qilletni.music.lastfm.entities.LastFmArtist;
import is.yarr.qilletni.music.lastfm.entities.LastFmTrack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class LastFmMusicCache implements MusicCache {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmMusicCache.class);

    private final LastFmMusicFetcher lastFmMusicFetcher;

    public LastFmMusicCache(LastFmMusicFetcher lastFmMusicFetcher) {
        this.lastFmMusicFetcher = lastFmMusicFetcher;
    }

    @Override
    public Optional<Track> getTrack(String name, String artist) {
        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession(); // Obtain the Hibernate Session

            var artistCompositeKey = new ArtistCompositeKey(name, artist);
            var trackOptional = Optional.<Track>ofNullable(session.find(LastFmTrack.class, artistCompositeKey));
            if (trackOptional.isPresent()) {
                LOGGER.debug("Found track in cache: {}", trackOptional.get());
                return trackOptional;
            }
        }

        return lastFmMusicFetcher.fetchTrack(name, artist)
                .map(LastFmTrack.class::cast)
                .map(this::storeTrack);
    }

    @Override
    public Optional<Track> getTrackById(String mbid) {
        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession(); // Obtain the Hibernate Session

            var builder = session.getCriteriaBuilder();
            var criteria = builder.createQuery(LastFmTrack.class);
            var root = criteria.from(LastFmTrack.class);

            // Assuming you are querying using the composite key components
            var trackIdPredicate = builder.equal(root.get("mbid"), mbid);

            criteria.where(trackIdPredicate);

            var tracks = session.createQuery(criteria).getResultList();

            if (!tracks.isEmpty()) {
                return Optional.of(tracks.getFirst());
            }
        }

        return lastFmMusicFetcher.fetchTrackById(mbid)
                .map(LastFmTrack.class::cast)
                .map(this::storeTrack);
    }

    @Override
    public List<Track> getTracks(List<MusicFetcher.TrackNameArtist> tracks) {
        return tracks.stream().map(track -> getTrack(track.name(), track.artist()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<Track> getTracksById(List<String> trackIds) {
        return trackIds.stream().map(this::getTrackById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Optional<Playlist> getPlaylist(String name, String author) {
        LOGGER.error("Last.fm does not support the collection syntax");
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> getPlaylistById(String id) {
        LOGGER.error("Last.fm does not support the collection syntax");
        return Optional.empty();
    }

    @Override
    public Optional<Album> getAlbum(String name, String artist) {
        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession();

            var artistCompositeKey = new ArtistCompositeKey(name, artist);
            var albumOptional = Optional.<Album>ofNullable(session.find(LastFmAlbum.class, artistCompositeKey));
            if (albumOptional.isPresent()) {
                LOGGER.debug("Found album in cache: {}", albumOptional.get());
                return albumOptional;
            }
        }

        return lastFmMusicFetcher.fetchAlbum(name, artist)
                .map(LastFmAlbum.class::cast)
                .map(this::storeAlbum);
    }

    @Override
    public Optional<Album> getAlbumById(String mbid) {
        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession(); // Obtain the Hibernate Session

            var builder = session.getCriteriaBuilder();
            var criteria = builder.createQuery(LastFmAlbum.class);
            var root = criteria.from(LastFmAlbum.class);

            // Assuming you are querying using the composite key components
            var albumIdPredicate = builder.equal(root.get("mbid"), mbid);

            criteria.where(albumIdPredicate);

            var albums = session.createQuery(criteria).getResultList();

            if (!albums.isEmpty()) {
                return Optional.of(albums.getFirst());
            }
        }

        return lastFmMusicFetcher.fetchAlbumById(mbid)
                .map(LastFmAlbum.class::cast)
                .map(this::storeAlbum);
    }

    @Override
    public List<Track> getAlbumTracks(Album album) {
        LOGGER.error("Last.Fm does not support fetching album tracks");
        return List.of();
    }

    @Override
    public List<Track> getPlaylistTracks(Playlist playlist) {
        LOGGER.error("Last.Fm does not support fetching playlist tracks");
        return List.of();
    }

    @Override
    public Optional<Artist> getArtistById(String id) {
        // Not the best, but Artists aren't cached due to very inconsistent IDs handled by Last.Fm
        return lastFmMusicFetcher.fetchArtistById(id);
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        // This isn't handled great as sometimes Last.Fm doesn't have an ID for an artist
        return Optional.of(new LastFmArtist("", "", name));
    }

    @Override
    public String getIdFromString(String idOrUrl) {
        return ""; // Not supported! An ID doesn't correlate to a URL
    }
    
    private Track storeTrack(LastFmTrack track) {
        LOGGER.debug("Storing track: {}", track);

        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession();
            
            // Simple enough just to do this every time
            storeAlbum((LastFmAlbum) track.getAlbum());

            var foundTrack = session.find(LastFmTrack.class, track.getCompositeKey());
            if (foundTrack != null) {
                LOGGER.debug("Already found track: {}", foundTrack);
                return foundTrack;
            }

            LOGGER.debug("Saving track: {}", track);
            session.save(track);
            return track;
        }
    }
    
    private Album storeAlbum(LastFmAlbum album) {
        LOGGER.debug("Storing album: {}", album);

        try (var entityTransaction = EntityTransaction.beginTransaction()) {
            var session = entityTransaction.getSession();

            var foundAlbum = session.find(LastFmAlbum.class, album.getCompositeKey());
            if (foundAlbum != null) {
                LOGGER.debug("Already found album: {}", foundAlbum);
                return foundAlbum;
            }

            LOGGER.debug("Saving album: {}", album);
            session.save(album);
            return album;
        }
    }
}
