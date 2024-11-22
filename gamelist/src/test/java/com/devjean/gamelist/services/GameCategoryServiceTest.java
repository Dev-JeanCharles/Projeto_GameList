package com.devjean.gamelist.services;

import com.devjean.gamelist.dto.GameCategoryDTO;
import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.utils.GameListCreator;
import com.devjean.gamelist.repositories.GameCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests of services")
class GameCategoryServiceTest {

    @Mock
    private GameCategoryRepository gameCategoryRepository;

    @InjectMocks
    private GameCategoryService gameCategoryService;

    @BeforeEach
    void setUp() {
        GameCategory mockGame = GameListCreator.createValidGame();
        List<GameCategory> mockGameCategory = List.of(mockGame);

        when(gameCategoryRepository.findAll()).thenReturn(mockGameCategory);
    }

    @Test
    @DisplayName("Deve retornar uma lista de GameListDTO quando o repositório retornar uma lista de GameList")
    void deveReturnarListaDeGameDTOQuandoRepositorioRetornarUmGameList() {
        List<GameCategoryDTO> result = gameCategoryService.findAll();

        assertEquals(1, result.size());
        assertEquals("Game 1", result.get(0).getName());
        assertEquals(1L, result.get(0).getId());

        verify(gameCategoryRepository, times(1)).findAll();
    }
}