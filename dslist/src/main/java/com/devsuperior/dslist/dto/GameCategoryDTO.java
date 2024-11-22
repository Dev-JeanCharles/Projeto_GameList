package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.entities.GameCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
public class GameCategoryDTO {
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    public GameCategoryDTO(GameCategory entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
