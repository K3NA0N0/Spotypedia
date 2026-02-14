package dev.ken.spotypedia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistDto {
    private String id;
    private String name;
    private int popularity;
    private String imageUrl;
}
