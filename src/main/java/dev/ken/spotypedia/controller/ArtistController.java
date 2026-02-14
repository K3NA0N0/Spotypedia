package dev.ken.spotypedia.controller;

import dev.ken.spotypedia.dto.ArtistDto;
import dev.ken.spotypedia.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("spotify/api")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtist(@PathVariable String id) {
        ArtistDto artist = artistService.getArtist(id);
        return ResponseEntity.ok(artist);
    }
}
