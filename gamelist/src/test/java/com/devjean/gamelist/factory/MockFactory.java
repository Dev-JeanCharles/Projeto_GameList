package com.devjean.gamelist.factory;

import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.projections.GameMinProjection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockFactory {
    public static GameMinProjection createGameMinProjection(Long id, String title) {
        GameMinProjection mock = mock(GameMinProjection.class);
        when(mock.getId()).thenReturn(id);
        when(mock.getTitle()).thenReturn(title);
        return mock;
    }

    public static GameDTO createGameDTO(Long id, String title) {
        GameDTO mock = mock(GameDTO.class);

        when(mock.getId()).thenReturn(id);
        when(mock.getTitle()).thenReturn(title);

        when(mock.getYear()).thenReturn(2024);
        when(mock.getGenre()).thenReturn("Esporte");
        when(mock.getPlatforms()).thenReturn("XBox");
        when(mock.getScore()).thenReturn(4.9);
        when(mock.getImgUrl()).thenReturn("https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/9.png");
        when(mock.getShortDescription()).thenReturn("This is a game");
        when(mock.getLongDescription()).thenReturn("Lorem ipsum dolor sit amet consectetur adipisicing elit");

        return mock;
    }
}
