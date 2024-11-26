package com.devjean.gamelist.application.web.dto;

import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.projections.GameMinProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
public class GameMinDTO {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Title is mandatory and cannot be blank.")
    private String title;

    @NotNull(message = "Year is mandatory.")
    private Integer year;

    @NotBlank(message = "Image URL is mandatory and cannot be blank.")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid URL format.")
    private String imgUrl;

    @NotBlank(message = "Short description is mandatory and cannot be blank.")
    @Size(max = 255, message = "Short description cannot exceed 255 characters.")
    private String shortDescription;

    public GameMinDTO(Game entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.year = entity.getYear();
        this.imgUrl = entity.getImgUrl();
        this.shortDescription = entity.getShortDescription();
    }

    public GameMinDTO(GameMinProjection projection) {
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.year = projection.getGameYear();
        this.imgUrl = projection.getImgUrl();
        this.shortDescription = projection.getShortDescription();
    }
}
