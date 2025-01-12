package is.yarr.qilletni.lib.lastfm;

import is.yarr.qilletni.api.auth.ServiceProvider;
import is.yarr.qilletni.api.lib.persistence.PackageConfig;
import is.yarr.qilletni.api.music.MusicCache;
import is.yarr.qilletni.api.music.MusicFetcher;
import is.yarr.qilletni.api.music.PlayActor;
import is.yarr.qilletni.api.music.StringIdentifier;
import is.yarr.qilletni.api.music.factories.AlbumTypeFactory;
import is.yarr.qilletni.api.music.factories.CollectionTypeFactory;
import is.yarr.qilletni.api.music.factories.SongTypeFactory;
import is.yarr.qilletni.api.music.orchestration.TrackOrchestrator;
import is.yarr.qilletni.music.lastfm.LastFmMusicCache;
import is.yarr.qilletni.music.lastfm.LastFmMusicFetcher;
import is.yarr.qilletni.music.lastfm.LastFmStringIdentifier;
import is.yarr.qilletni.music.lastfm.api.LastFmAPI;
import is.yarr.qilletni.music.lastfm.api.Page;
import is.yarr.qilletni.music.lastfm.api.Period;
import is.yarr.qilletni.music.lastfm.api.responses.DateRange;
import is.yarr.qilletni.music.lastfm.api.responses.reusable.FullArtistResponse;
import is.yarr.qilletni.music.lastfm.auth.LastFmAuthorizer;
import is.yarr.qilletni.music.lastfm.play.ReroutablePlayActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class LastFmServiceProvider implements ServiceProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmServiceProvider.class);

    private PackageConfig packageConfig;
    private LastFmMusicCache musicCache;
    private MusicFetcher musicFetcher;
    private TrackOrchestrator trackOrchestrator;
    private StringIdentifier stringIdentifier;
    private LastFmAuthorizer authorizer;

    @Override
    public CompletableFuture<Void> initialize(BiFunction<PlayActor, MusicCache, TrackOrchestrator> defaultTrackOrchestratorFunction, PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
        initConfig();

        authorizer = new LastFmAuthorizer(packageConfig, packageConfig.getOrThrow("apiKey"), packageConfig.getOrThrow("apiSecret"));

        return authorizer.authorizeLastFm().thenAccept(lastFmAPI -> {
            
            sayHello(lastFmAPI);
            
            var lastFmMusicFetcher = new LastFmMusicFetcher();
            musicFetcher = lastFmMusicFetcher;
            musicCache = new LastFmMusicCache(lastFmMusicFetcher);
            trackOrchestrator = defaultTrackOrchestratorFunction.apply(new ReroutablePlayActor(), musicCache);
        });
        
//        return authorizer.authorizeSpotify().thenRun(() -> {
//            var spotifyMusicFetcher = new SpotifyMusicFetcher(authorizer);
//            SpotifyApiSingleton.setSpotifyAuthorizer(authorizer);
//            musicFetcher = spotifyMusicFetcher;
//            musicCache = new SpotifyMusicCache(spotifyMusicFetcher);
//            trackOrchestrator = defaultTrackOrchestratorFunction.apply(new ReroutablePlayActor(), musicCache);
//        });
    }
    
    private void sayHello(LastFmAPI lastFmAPI) {
//        lastFmAPI.getAlbumInfo("Knocked Loose", "A Tear in the Fabric of Life").join();
        var friendsResponse = lastFmAPI.getWeeklyTrackChart("RubbaBoy", new DateRange()).join();
        
        if (friendsResponse.isError()) {
            LOGGER.error("Error fetching: {}", friendsResponse.getErrorResponse());
            return;
        }
        
        var friends = friendsResponse.getResponse();

        System.out.println("friends = " + friends);

        for (var artist : friends.weeklytrackchart().track()) {
            System.out.println("\t%s  (%s plays) - %s".formatted(artist.name(), artist.playcount(), artist.mbid()));
        }

//        System.out.println("res = " + friends);
        
//        if (albumResponse.isError()) {
//            LOGGER.error("Error fetching albums: {}", albumResponse.getErrorResponse());
//            return;
//        }
//
//        var response = albumResponse.getResponse();
//
//        System.out.println("albums = " + response.topalbums());
    }

    @Override
    public void shutdown() {
//        authorizer.shutdown();
//        ExecutorServiceUtility.shutdown(PlaylistCreator.EXECUTOR_SERVICE);
    }

    @Override
    public String getName() {
        return "LastFm";
    }

    @Override
    public MusicCache getMusicCache() {
        return Objects.requireNonNull(musicCache, "ServiceProvider#initialize must be invoked to initialize MusicCache");
    }

    @Override
    public MusicFetcher getMusicFetcher() {
        return Objects.requireNonNull(musicFetcher, "ServiceProvider#initialize must be invoked to initialize MusicFetcher");
    }

    @Override
    public TrackOrchestrator getTrackOrchestrator() {
        return Objects.requireNonNull(trackOrchestrator, "ServiceProvider#initialize must be invoked to initialize TrackOrchestrator");
    }

    @Override
    public StringIdentifier getStringIdentifier(SongTypeFactory songTypeFactory, CollectionTypeFactory collectionTypeFactory, AlbumTypeFactory albumTypeFactory) {
        if (stringIdentifier == null) {
            return stringIdentifier = new LastFmStringIdentifier(musicCache, songTypeFactory, collectionTypeFactory, albumTypeFactory);
        }

        return stringIdentifier;
    }

    private void initConfig() {
//        packageConfig.loadConfig();
//
//        var requiredOptions = List.of("clientId", "redirectUri", "dbUrl", "dbUsername", "dbPassword");
//        var allFound = true;
//
//        for (var option : requiredOptions) {
//            if (packageConfig.get(option).isEmpty()) {
//                allFound = false;
//                LOGGER.error("Required config value '{}' not found in Spotify config", option);
//            }
//        }
//
//        if (!allFound) {
//            throw new ConfigInitializeException("Spotify config is missing required options, aborting");
//        }

//        HibernateUtil.initializeSessionFactory(packageConfig.getOrThrow("dbUrl"), packageConfig.getOrThrow("dbUsername"), packageConfig.getOrThrow("dbPassword"));
    }

}
