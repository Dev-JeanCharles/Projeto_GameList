package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.Entities.Game;
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
        id = entity.getId();
        title = entity.getTitle();
        year = entity.getYear();
        imgUrl = entity.getImgUrl();
        shortDescription = entity.getShortDescription();
    }
}
