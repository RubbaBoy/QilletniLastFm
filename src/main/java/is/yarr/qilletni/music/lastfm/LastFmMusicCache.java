package is.yarr.qilletni.music.lastfm;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.MusicCache;
import is.yarr.qilletni.api.music.MusicFetcher;
import is.yarr.qilletni.api.music.Playlist;
import is.yarr.qilletni.api.music.Track;

import java.util.List;
import java.util.Optional;

public class LastFmMusicCache implements MusicCache {

    private final LastFmMusicFetcher lastFmMusicFetcher;

    public LastFmMusicCache(LastFmMusicFetcher lastFmMusicFetcher) {
        this.lastFmMusicFetcher = lastFmMusicFetcher;
    }

    @Override
    public Optional<Track> getTrack(String name, String artist) {
        return Optional.empty();
    }

    @Override
    public Optional<Track> getTrackById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Track> getTracks(List<MusicFetcher.TrackNameArtist> tracks) {
        return List.of();
    }

    @Override
    public List<Track> getTracksById(List<String> trackIds) {
        return List.of();
    }

    @Override
    public Optional<Playlist> getPlaylist(String name, String author) {
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> getPlaylistById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> getAlbum(String name, String artist) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> getAlbumById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Track> getAlbumTracks(Album album) {
        return List.of();
    }

    @Override
    public List<Track> getPlaylistTracks(Playlist playlist) {
        return List.of();
    }

    @Override
    public Optional<Artist> getArtistById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return Optional.empty();
    }

    @Override
    public String getIdFromString(String idOrUrl) {
        return "";
    }
}
