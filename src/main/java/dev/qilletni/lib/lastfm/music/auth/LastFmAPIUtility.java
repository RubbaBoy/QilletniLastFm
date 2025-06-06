package dev.qilletni.lib.lastfm.music.auth;

import dev.qilletni.lib.lastfm.exceptions.LastFmAPIErrorException;
import dev.qilletni.lib.lastfm.music.api.responses.LastFmResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

public class LastFmAPIUtility {

    public static String generateSignature(Map<String, String> params, String apiSecret) {
        var concatenated = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + entry.getValue())
                .collect(Collectors.joining());
        
        concatenated += apiSecret;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(concatenated.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    public static String toQueryString(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
    
    public static <T> T verifySuccess(LastFmResponse<T> lastFmResponse) {
        if (lastFmResponse.isError()) {
            var error = lastFmResponse.getErrorResponse();
            throw new LastFmAPIErrorException("LastFm responses with error code %d: %s".formatted(error.error(), error.message()));
        }
        
        return lastFmResponse.getResponse();
    }
    
}
