package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.Entities.Game;
import lombok.*;

@Data
@NoArgsConstructor
@Getter
public class GameDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private Integer year;
    private String imgUrl;
    private String shortDescription;

    public GameDTO(Game entity) {
        id = entity.getId();
        title = entity.getTitle();
        year = entity.getYear();
        imgUrl = entity.getImgUrl();
        shortDescription = entity.getShortDescription();
    }
}
