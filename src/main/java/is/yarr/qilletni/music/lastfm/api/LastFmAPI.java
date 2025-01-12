package is.yarr.qilletni.music.lastfm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import is.yarr.qilletni.music.lastfm.api.responses.AlbumInfoResponse;
import is.yarr.qilletni.music.lastfm.api.responses.DateRange;
import is.yarr.qilletni.music.lastfm.api.responses.GetFriendsResponse;
import is.yarr.qilletni.music.lastfm.api.responses.ErrorResponse;
import is.yarr.qilletni.music.lastfm.api.responses.GetLovedTracksResponse;
import is.yarr.qilletni.music.lastfm.api.responses.GetRecentTracksResponse;
import is.yarr.qilletni.music.lastfm.api.responses.GetTokenResponse;
import is.yarr.qilletni.music.lastfm.api.responses.GetTopArtistsResponse;
import is.yarr.qilletni.music.lastfm.api.responses.LastFmResponse;
import is.yarr.qilletni.music.lastfm.api.responses.GetTopAlbumsResponse;
import is.yarr.qilletni.music.lastfm.auth.LastFmAPIUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LastFmAPI {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LastFmAPI.class);

    private static final String API_URL = "https://ws.audioscrobbler.com/2.0/";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static LastFmAPI instance;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiKey;
    private final String apiSecret;
    private final String sessionToken;

    public LastFmAPI(String apiKey, String apiSecret, String sessionToken) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.sessionToken = sessionToken;

        instance = this;
    }

    public static CompletableFuture<LastFmAPI> createNewAPI(String authToken, String apiKey, String apiSecret) {
        return getSessionToken(apiKey, apiSecret, authToken).thenApply(sessionToken ->
                new LastFmAPI(apiKey, apiSecret, sessionToken));
    }

    private static CompletableFuture<String> getSessionToken(String apiKey, String apiSecret, String authToken) {
        try (var client = HttpClient.newHttpClient()) {
            return makeRawLastFmRequest(client, "auth.getSession", apiKey, apiSecret, Map.of("token", authToken))
                    .thenApply(HttpResponse::body)
                    .thenApply(response -> {
                        var sessionResponse = gson.fromJson(response, GetTokenResponse.class);
                        return sessionResponse.session().key();
                    });
        }
    }

    private static CompletableFuture<HttpResponse<String>> makeRawLastFmRequest(HttpClient httpClient, String method, String apiKey, String apiSecret, Map<String, String> additionalParams) {
        var params = new HashMap<>(Map.of(
                "method", method,
                "api_key", apiKey
        ));

        params.putAll(additionalParams);

        var apiSig = LastFmAPIUtility.generateSignature(params, apiSecret);
        params.put("api_sig", apiSig);
        params.put("format", "json");

        var postData = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        var request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public static LastFmAPI getInstance() {
        return instance;
    }

    private CompletableFuture<HttpResponse<String>> makeLastFmRequest(String method, Map<String, String> additionalParams) {
        return makeRawLastFmRequest(httpClient, method, apiKey, apiSecret, additionalParams);
    }

    public CompletableFuture<Void> getUserInfo() {
        var completableFuture = new CompletableFuture<Void>();

        makeLastFmRequest("user.getInfo", Map.of("user", "RubbaBoy"))
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println("response = " + response);
                    completableFuture.complete(null);
                });

        return completableFuture;
    }

    public CompletableFuture<LastFmResponse<AlbumInfoResponse>> getAlbumInfo(String artist, String album) {
        return makeLastFmRequest("album.getInfo", Map.of("artist", artist, "album", album))
                .thenApply(HttpResponse::body)
                .thenApply(response -> this.<AlbumInfoResponse>checkErrorResponse(response)
                        .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, AlbumInfoResponse.class))));
    }

    // TODO: Getting friends does NOT support `recenttracks` yet
    public CompletableFuture<LastFmResponse<GetFriendsResponse>> getFriends(String user, Page page) {
        var params = new LastFmParams(Map.of("user", user))
                .setPage(page);

        return makeLastFmRequest("user.getFriends", params.getMap())
                .thenApply(HttpResponse::body)
                .thenApply(response ->
                        this.<GetFriendsResponse>checkErrorResponse(response)
                                .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, GetFriendsResponse.class))));
    }

    public CompletableFuture<LastFmResponse<GetLovedTracksResponse>> getLovedTracks(String user, Page page) {
        var params = new LastFmParams(Map.of("user", user))
                .setPage(page);

        return makeLastFmRequest("user.getLovedTracks", params.getMap())
                .thenApply(HttpResponse::body)
                .thenApply(response ->
                        this.<GetLovedTracksResponse>checkErrorResponse(response)
                                .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, GetLovedTracksResponse.class))));
    }

    public CompletableFuture<LastFmResponse<GetRecentTracksResponse>> getRecentTracks(String user, boolean extended, DateRange dateRange, Page page) {
        if (extended) {
            LOGGER.error("Extended recent tracks not supported yet");
            extended = false;
        }
        
        var params = new LastFmParams(Map.of(
                "user", user 
//                "extended", extended ? "1" : "0"
        ))
                .setPage(page)
                .setDateRange(dateRange);

        return makeLastFmRequest("user.getRecentTracks", params.getMap())
                .thenApply(HttpResponse::body)
                .thenApply(response ->
                        this.<GetRecentTracksResponse>checkErrorResponse(response)
                                .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, GetRecentTracksResponse.class))));
    }

    public CompletableFuture<LastFmResponse<GetTopAlbumsResponse>> getTopAlbums(String user) {
        return getTopAlbums(user, Period.UNSET, new Page());
    }

    public CompletableFuture<LastFmResponse<GetTopAlbumsResponse>> getTopAlbums(String user, Period period, Page page) {
        var params = new LastFmParams(Map.of("user", user))
                .setPage(page)
                .setPeriod(period);

        return makeLastFmRequest("user.getTopAlbums", params.getMap())
                .thenApply(HttpResponse::body)
                .thenApply(response ->
                        this.<GetTopAlbumsResponse>checkErrorResponse(response)
                                .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, GetTopAlbumsResponse.class))));
    }

    public CompletableFuture<LastFmResponse<GetTopArtistsResponse>> getTopArtists(String user, Period period, Page page) {
        var params = new LastFmParams(Map.of(
                "user", user 
        ))
                .setPage(page)
                .setPeriod(period);

        return makeLastFmRequest("user.getTopArtists", params.getMap())
                .thenApply(HttpResponse::body)
                .thenApply(response ->
                        this.<GetTopArtistsResponse>checkErrorResponse(response)
                                .orElseGet(() -> new LastFmResponse<>(gson.fromJson(response, GetTopArtistsResponse.class))));
    }

    private <T> Optional<LastFmResponse<T>> checkErrorResponse(String response) {
        // Maybe a bit silly, but this is quite efficient
        try (var reader = new JsonReader(new StringReader(response))) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("error")) {
                    return Optional.of(new LastFmResponse<>(gson.fromJson(response, ErrorResponse.class)));
                } else {
                    reader.skipValue();
                }
            }
        } catch (Exception ignored) {}

        return Optional.empty();
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
