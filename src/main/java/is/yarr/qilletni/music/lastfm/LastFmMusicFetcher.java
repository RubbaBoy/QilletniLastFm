package is.yarr.qilletni.music.lastfm;

import is.yarr.qilletni.api.music.Album;
import is.yarr.qilletni.api.music.Artist;
import is.yarr.qilletni.api.music.MusicFetcher;
import is.yarr.qilletni.api.music.Playlist;
import is.yarr.qilletni.api.music.Track;
import is.yarr.qilletni.music.lastfm.api.LastFmAPI;
import is.yarr.qilletni.music.lastfm.api.responses.AlbumInfoResponse;
import is.yarr.qilletni.music.lastfm.api.responses.ErrorCode;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.FullTrackResponse;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.SimpleArtistResponse;
import is.yarr.qilletni.music.lastfm.auth.LastFmAPIUtility;
import is.yarr.qilletni.music.lastfm.entities.LastFmAlbum;
import is.yarr.qilletni.music.lastfm.entities.LastFmArtist;
import is.yarr.qilletni.music.lastfm.entities.LastFmTrack;
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
        var trackAPIResponse = lastFmAPI.getTrackInfo(name, artist, "", false).join();
        if (trackAPIResponse.isError() && trackAPIResponse.getErrorResponse().getErrorCode() == ErrorCode.INVALID_PARAMETERS) { // Track not found
            return Optional.empty();
        }
        
        var trackResponse = LastFmAPIUtility.verifySuccess(trackAPIResponse);

        // Track wasn't found (LastFM doesn't explicitly state if nothing was found)
        var track = trackResponse.track();
        if (track == null) {
            return Optional.empty();
        }

        var lastFmArtist = createArtist(track.artist());
        return Optional.of(new LastFmTrack(track.mbid(), track.name(), lastFmArtist, createAlbum(track.album(), lastFmArtist), (int) track.duration()));
    }

    @Override
    public Optional<Track> fetchTrackById(String mbid) {
        var trackAPIResponse = lastFmAPI.getTrackInfo(mbid).join();
        if (trackAPIResponse.isError() && trackAPIResponse.getErrorResponse().getErrorCode() == ErrorCode.INVALID_PARAMETERS) { // Track not found
            return Optional.empty();
        }
        
        var trackResponse = LastFmAPIUtility.verifySuccess(trackAPIResponse);
        
        // Track wasn't found (LastFM doesn't explicitly state if nothing was found)
        var track = trackResponse.track();
        if (track == null) {
            return Optional.empty();
        }
        
        var lastFmArtist = createArtist(track.artist());
        return Optional.of(new LastFmTrack(track.mbid(), track.name(), lastFmArtist, createAlbum(track.album(), lastFmArtist), (int) track.duration()));
    }

    @Override
    public List<Track> fetchTracks(List<TrackNameArtist> tracks) {
        return tracks.parallelStream()
                .map(trackNameArtist -> fetchTrack(trackNameArtist.name(), trackNameArtist.artist()))
                .filter(Optional::isPresent)
                .map(Optional::get).toList();
    }

    @Override
    public List<Track> fetchTracksById(List<String> trackIds) {
        return trackIds.parallelStream().map(this::fetchTrackById)
                .filter(Optional::isPresent).map(Optional::get).toList();
    }

    @Override
    public Optional<Playlist> fetchPlaylist(String name, String author) {
        LOGGER.error("Last.fm does not support the collection syntax");
        return Optional.empty();
    }

    @Override
    public Optional<Playlist> fetchPlaylistById(String id) {
        LOGGER.error("Last.fm does not support the collection syntax");
        return Optional.empty();
    }

    @Override
    public Optional<Album> fetchAlbum(String name, String artist) {
        var albumApiResponse = lastFmAPI.getAlbumInfo(name, artist).join(); // TODO: check with album not found

        var albumResponse = LastFmAPIUtility.verifySuccess(albumApiResponse);
        
        var album = albumResponse.album();
        if (album == null) {
            return Optional.empty();
        }
        
        return Optional.of(createAlbum(album, createArtist(album.artist())));
    }

    @Override
    public Optional<Album> fetchAlbumById(String mbid) {
        var albumApiResponse = lastFmAPI.getAlbumInfo(mbid).join(); // TODO: check with album not found

        var albumResponse = LastFmAPIUtility.verifySuccess(albumApiResponse);

        var album = albumResponse.album();
        if (album == null) {
            return Optional.empty();
        }

        return Optional.of(createAlbum(album, createArtist(album.artist())));
    }

    @Override
    public List<Track> fetchAlbumTracks(Album album) {
        LOGGER.error("Last.Fm does not support fetching album tracks");
        return List.of();
    }

    @Override
    public List<Track> fetchPlaylistTracks(Playlist playlist) {
        LOGGER.error("Last.Fm does not support fetching playlist tracks");
        return List.of();
    }

    @Override
    public Optional<Artist> fetchArtistByName(String name) {
        return Optional.of(new LastFmArtist("", "", name));
    }

    @Override
    public Optional<Artist> fetchArtistById(String mbid) {
        var artistApiResponse = lastFmAPI.getArtistInfo(mbid).join(); // TODO: check with album not found

        var artistResponse = LastFmAPIUtility.verifySuccess(artistApiResponse);

        var artist = artistResponse.artist();
        if (artist == null) {
            return Optional.empty();
        }

        return Optional.of(createArtist(artist));
    }
    
    private static LastFmArtist createArtist(SimpleArtistResponse artist) {
        return new LastFmArtist(artist.mbid(), artist.url(), artist.name());
    }
    
    private static LastFmArtist createArtist(String artistName) {
        return new LastFmArtist("", "", artistName);
    }
    
    private static LastFmAlbum createAlbum(FullTrackResponse.AlbumResponse album, LastFmArtist artist) {
        return new LastFmAlbum("", album.title(), artist.getName());
    }

    private static LastFmAlbum createAlbum(AlbumInfoResponse.AlbumResponse album, LastFmArtist artist) {
        return new LastFmAlbum(album.mbid(), album.name(), artist.getName());
    }
}
