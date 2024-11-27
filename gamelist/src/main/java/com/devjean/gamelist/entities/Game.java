package com.devjean.gamelist.entities;

import com.devjean.gamelist.application.web.dto.GameDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_game")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    @Column(name = "game_year")
    private Integer year;
    private String genre;
    private String platforms;
    private Double score;
    private String imgUrl;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String longDescription;

    public static Game fromDTO(GameDTO gameDTO) {
        Game game = new Game();
        game.setTitle(gameDTO.getTitle());
        game.setYear(gameDTO.getYear());
        game.setGenre(gameDTO.getGenre());
        game.setPlatforms(gameDTO.getPlatforms());
        game.setScore(gameDTO.getScore());
        game.setImgUrl(gameDTO.getImgUrl());
        game.setShortDescription(gameDTO.getShortDescription());
        game.setLongDescription(gameDTO.getLongDescription());
        return game;
    }
}
