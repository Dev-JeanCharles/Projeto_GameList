package com.devjean.gamelist.utils;


import com.devjean.gamelist.entities.GameCategory;

public class GameListCreator {
    public static GameCategory createValidGame() {
        return GameCategory.builder()
                .id(1L)
                .name("Game 1")
                .build();
    }
}
