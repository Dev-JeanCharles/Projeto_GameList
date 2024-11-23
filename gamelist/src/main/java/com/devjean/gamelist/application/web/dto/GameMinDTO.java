package com.devjean.gamelist.application.web.dto;

import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.projections.GameMinProjection;
import lombok.*;

@NoArgsConstructor
@Getter
public class GameMinDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private Integer year;
    private String imgUrl;
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
