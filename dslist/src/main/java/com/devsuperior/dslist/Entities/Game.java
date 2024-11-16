package com.devsuperior.dslist.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_game")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    @Column(name = "game_year")
    private Integer year;
    private String gender;
    private String platforms;
    private Double score;
    private String imgUrl;
    private String shortDescription;
    private String longDescription;
}
