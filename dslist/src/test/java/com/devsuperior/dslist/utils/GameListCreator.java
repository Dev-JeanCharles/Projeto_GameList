package com.devsuperior.dslist.utils;


import com.devsuperior.dslist.entities.GameCategory;

public class GameListCreator {
    public static GameCategory createValidGame() {
        return GameCategory.builder()
                .id(1L)
                .name("Game 1")
                .build();
    }
}
