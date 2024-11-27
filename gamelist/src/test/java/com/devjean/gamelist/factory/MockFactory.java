package com.devjean.gamelist.factory;

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
}
