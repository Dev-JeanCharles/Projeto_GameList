package com.devjean.gamelist.dto;

import com.devjean.gamelist.entities.GameCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor
public class GameCategoryDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    public GameCategoryDTO(GameCategory entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
