package com.devsuperior.dslist.mocks;


import com.devsuperior.dslist.entities.GameList;

public class GameListMock {
    public static GameList createValidGame() {
        return GameList.builder()
                .id(1L)
                .name("Game 1")
                .build();
    }
}
