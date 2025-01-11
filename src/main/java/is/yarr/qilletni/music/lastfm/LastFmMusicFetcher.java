package is.yarr.qilletni.music.lastfm;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.MusicFetcher;
import is.yarr.qilletni.api.music.Playlist;
import is.yarr.qilletni.api.music.Track;
import is.yarr.qilletni.music.lastfm.api.LastFmAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class LastFmMusicFetcher implements MusicFetcher {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmMusicFetcher.class);
    
    private final LastFmAPI lastFmAPI;

    public LastFmMusicFetcher() {
        this.lastFmAPI = LastFmAPI.getInstance();
    }

    @Override
    public Optional<Track> fetchTrack(String name, String artist) {
        return Optional.empty();
    }

    @Override
    public Optional<Track> fetchTrackById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Track> fetchTracks(List<TrackNameArtist> tracks) {
        return List.of();
    }

    @Override
    public List<Track> fetchTracksById(List<String> trackIds) {
        return List.of();
    }

    @Override
    public Optional<Playlist> fetchPlaylist(String name, String author) {
        LOGGER.error("Last.fm does not support the collection syntax");
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> fetchPlaylistById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> fetchAlbum(String name, String artist) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> fetchAlbumById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Track> fetchAlbumTracks(Album album) {
        return List.of();
    }

    @Override
    public List<Track> fetchPlaylistTracks(Playlist playlist) {
        return List.of();
    }

    @Override
    public Optional<Artist> fetchArtistByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Artist> fetchArtistById(String id) {
        return Optional.empty();
    }
}
