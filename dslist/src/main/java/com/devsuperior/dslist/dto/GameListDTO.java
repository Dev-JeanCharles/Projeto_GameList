package com.devsuperior.dslist.dto;

import com.devsuperior.dslist.Entities.GameList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Getter
public class GameListDTO {
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    public GameListDTO(GameList entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
