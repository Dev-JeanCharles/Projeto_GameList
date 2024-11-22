package com.devsuperior.dslist.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_belonging")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Belonging {
    @EqualsAndHashCode.Include
    @EmbeddedId
    private BelongingTK id = new BelongingTK();

    private Integer position;

    public Belonging(Game game, GameCategory gameCategory, Integer position) {
        id.setGame(game);
        id.setGameCategory(gameCategory);
        this.position = position;
    }
}
