package dev.ken.spotypedia.service;

import dev.ken.spotypedia.dto.ArtistDto;
import dev.ken.spotypedia.dto.TokenResponse;
import dev.ken.spotypedia.exception.SearchException;
import dev.ken.spotypedia.exception.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ArtistService {
    private final WebClient webClient;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    public ArtistService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ArtistDto getArtist(String id) {
        String validToken = getToken();

        ArtistDto artist = webClient.get()
                .uri("https://api.spotify.com/v1/artists/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                .retrieve()
                .bodyToMono(ArtistDto.class)
                .block();

        if (artist == null) {
            throw new SearchException("Could not get artist");
        }
        return artist;
    }

    private String getToken() {
        try {
            TokenResponse response =  webClient.post()
                    .uri("https://accounts.spotify.com/api/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret))
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .block();

            if (response == null || response.getAccess_token() == null || response.getAccess_token().isBlank()) {
                throw new TokenException("Spotify returned no access_token");
            }

            return response.getAccess_token();
        } catch (Exception e) {
            throw new TokenException("Could not retrieve token");
        }
    }
}
