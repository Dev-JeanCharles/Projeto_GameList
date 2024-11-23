package com.devjean.gamelist.utils;


import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.entities.GameCategory;

public class GameListCreator {
    public static GameCategory createValidGameCategory() {
        return GameCategory.builder()
                .id(1L)
                .name("Esporte")
                .build();
    }
    public static Game createValidGame() {
        return Game.builder()
                .id(1L)
                .title("Game 1")
                .year(2024)
                .genre("Esporte")
                .platforms("XBox")
                .score(4.9)
                .imgUrl("https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/9.png")
                .shortDescription("This is a game")
                .longDescription("Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi")
                .build();
    }
}
