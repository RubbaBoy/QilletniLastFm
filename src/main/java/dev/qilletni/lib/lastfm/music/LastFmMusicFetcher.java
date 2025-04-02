package dev.qilletni.lib.lastfm.music;

import dev.qilletni.api.music.Album;
import dev.qilletni.api.music.Artist;
import dev.qilletni.api.music.MusicFetcher;
import dev.qilletni.api.music.Playlist;
import dev.qilletni.api.music.Track;
import dev.qilletni.lib.lastfm.music.api.LastFmAPI;
import dev.qilletni.lib.lastfm.music.api.responses.AlbumInfoResponse;
import dev.qilletni.lib.lastfm.music.api.responses.ErrorCode;
import dev.qilletni.lib.lastfm.music.api.responses.GetTopAlbumsResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.FullArtistResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.FullTrackResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.LovedTrackResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.RecentTrackResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.TopTrackResponse;
import dev.qilletni.lib.lastfm.music.api.responses.reusable.generic.GenericArtistResponse;
import dev.qilletni.lib.lastfm.music.auth.LastFmAPIUtility;
import dev.qilletni.lib.lastfm.music.entities.LastFmAlbum;
import dev.qilletni.lib.lastfm.music.entities.LastFmArtist;
import dev.qilletni.lib.lastfm.music.entities.LastFmTrack;
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

        return Optional.of(createTrack(track));
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
        
        return Optional.of(createTrack(track));
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

    public static LastFmTrack createTrack(FullTrackResponse track) {
        var lastFmArtist = createArtist(track.artist());
        return new LastFmTrack(track.mbid(), track.name(), lastFmArtist, createAlbum(track.album(), lastFmArtist), (int) track.duration());
    }

    public static LastFmTrack createTrack(RecentTrackResponse track) {
        var lastFmArtist = createArtist(track.artist().text(), track.artist().mbid());
        return new LastFmTrack(track.mbid(), track.name(), lastFmArtist, createAlbum(track.album().mbid(), track.album().text(), lastFmArtist), 0);
    }

    public static LastFmTrack createTrack(LovedTrackResponse track) {
        var lastFmArtist = createArtist(track.artist());
        return new LastFmTrack(track.mbid(), track.name(), lastFmArtist, null, 0);
    }

    public static LastFmTrack createTrack(TopTrackResponse track) {
        var lastFmArtist = createArtist(track.artist());
        return new LastFmTrack(track.mbid(), track.name(), lastFmArtist, null, track.duration());
    }

    public static LastFmArtist createArtist(GenericArtistResponse artist) {
        return new LastFmArtist(artist.mbid(), artist.url(), artist.name());
    }

    public static LastFmArtist createArtist(String artistName, String mbid) {
        return new LastFmArtist(mbid, "", artistName);
    }

    public static LastFmArtist createArtist(String artistName) {
        return new LastFmArtist("", "", artistName);
    }

    public static LastFmArtist createArtist(FullArtistResponse topArtistResponse) {
        return new LastFmArtist(topArtistResponse.mbid(), topArtistResponse.url(), topArtistResponse.name());
    }

    public static LastFmAlbum createAlbum(FullTrackResponse.AlbumResponse album, LastFmArtist artist) {
        return new LastFmAlbum("", album.title(), artist.getName());
    }

    public static LastFmAlbum createAlbum(AlbumInfoResponse.AlbumResponse album, LastFmArtist artist) {
        return new LastFmAlbum(album.mbid(), album.name(), artist.getName());
    }

    public static LastFmAlbum createAlbum(GetTopAlbumsResponse.Album topAlbumResponse) {
        return new LastFmAlbum(topAlbumResponse.mbid(), topAlbumResponse.name(), topAlbumResponse.artist().name());
    }

    public static LastFmAlbum createAlbum(String mbid, String name, LastFmArtist artist) {
        return new LastFmAlbum(mbid, name, artist.getName());
    }
}
