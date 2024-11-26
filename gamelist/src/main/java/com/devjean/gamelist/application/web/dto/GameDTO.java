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

    @NotBlank(message = "Title is mandatory and cannot be blank.")
    private String title;

    @NotNull(message = "Year is mandatory.")
    private Integer year;

    @NotBlank(message = "Genre is mandatory and cannot be blank.")
    private String genre;

    @NotBlank(message = "Platforms are mandatory and cannot be blank.")
    private String platforms;

    @NotNull(message = "Score is mandatory.")
    @DecimalMin(value = "0.0", message = "Score must be at least 0.")
    @DecimalMax(value = "10.0", message = "Score must be at most 10.")
    private Double score;

    @NotBlank(message = "Image URL is mandatory and cannot be blank.")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid URL format.")
    private String imgUrl;

    @NotBlank(message = "Short description is mandatory and cannot be blank.")
    @Size(max = 255, message = "Short description cannot exceed 255 characters.")
    private String shortDescription;

    @NotBlank(message = "Long description is mandatory and cannot be blank.")
    private String longDescription;

    public GameDTO(Game entity) {
        BeanUtils.copyProperties(entity, this);
    }
}
