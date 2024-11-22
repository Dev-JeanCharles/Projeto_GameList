package com.devsuperior.dslist.utils;


import com.devsuperior.dslist.entities.GameList;

public class GameListCreator {
    public static GameList createValidGame() {
        return GameList.builder()
                .id(1L)
                .name("Game 1")
                .build();
    }
}
