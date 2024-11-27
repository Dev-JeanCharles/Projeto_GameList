package com.devjean.gamelist.application.web.dto;

import com.devjean.gamelist.entities.Game;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Getter
@Setter
public class GameDTO {
    private Long id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title must not exceed 255 characters.")
    private String title;

    @NotNull(message = "Year cannot be null.")
    @Min(value = 1950, message = "Year must be after 1950.")
    @Max(value = 2100, message = "Year must be before 2100.")
    private Integer year;

    @NotBlank(message = "Genre cannot be blank.")
    @Size(max = 100, message = "Genre must not exceed 100 characters.")
    private String genre;

    @NotBlank(message = "Platforms cannot be blank.")
    private String platforms;

    @NotNull(message = "Score cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Score must be at least 0.")
    @DecimalMax(value = "10.0", inclusive = true, message = "Score must be at most 10.")
    private Double score;

    @NotBlank(message = "Image URL cannot be blank.")
    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL.")
    private String imgUrl;

    @NotBlank(message = "Short description cannot be blank.")
    @Size(max = 255, message = "Short description must not exceed 255 characters.")
    private String shortDescription;

    @NotBlank(message = "Long description cannot be blank.")
    @Size(max = 5000, message = "Long description must not exceed 5000 characters.")
    private String longDescription;

    public GameDTO(Game entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
