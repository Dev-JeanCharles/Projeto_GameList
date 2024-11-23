package com.devjean.gamelist.services;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.commons.IllegalArgumentException;
import com.devjean.gamelist.application.web.dto.GameCategoryDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.projections.GameMinProjection;
import com.devjean.gamelist.repositories.GameCategoryRepository;
import com.devjean.gamelist.repositories.GameRepository;
import com.devjean.gamelist.utils.GameListCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests of services")
class GameCategoryServiceTest {

    @Mock
    private GameCategoryRepository gameCategoryRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameCategoryService gameCategoryService;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        // Mock de GameCategory
        GameCategory mockCategory = GameListCreator.createValidGameCategory();
        List<GameCategory> mockGameCategory = List.of(mockCategory);

        when(gameCategoryRepository.findAll()).thenReturn(mockGameCategory);

        // Mock de Game
        Game mockGame = GameListCreator.createValidGame();
        List<Game> mockGames = List.of(mockGame);

//        when(gameRepository.findById(1L)).thenReturn(Optional.of(mockGame));
        when(gameRepository.findAll()).thenReturn(mockGames);
    }

    @Nested
    @DisplayName("Game Categories Tests")
    class GameCategoriesTests {

        @Test
        @DisplayName("Deve retornar uma lista de GameListDTO quando o repositório retornar uma lista de GameList")
        void deveRetornarListaDeGameDTOQuandoRepositorioRetornarUmGameList() {
            // Act
            List<GameCategoryDTO> result = gameCategoryService.findAllCategories();

            // Assert
            assertEquals(1, result.size());
            assertEquals("Esporte", result.get(0).getName());
            assertEquals(1L, result.get(0).getId());

            // Verify
            verify(gameCategoryRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando nenhuma categoria for encontrada")
        void deveRetornarEntityNotFoundExceptionQuandoNenhumaCategoriaForEncontrada() {
            // Arrange
            when(gameCategoryRepository.findAll()).thenReturn(Collections.emptyList());

            // Act & Assert
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->{
                gameCategoryService.findAllCategories();
            });

            // Assert
            assertEquals("No categories found.", ex.getMessage());

            // Verify
            verify(gameCategoryRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve mover um jogo de um índice para outro dentro da categoria")
        void deveMoverJogoDentroDaCategoria() {
            // Arrange
            Long validCategoryId = 1L;

            // Mock de GameMinProjection
            GameMinProjection game1 = mock(GameMinProjection.class);
            when(game1.getId()).thenReturn(1L);
            when(game1.getTitle()).thenReturn("Game 1");

            GameMinProjection game2 = mock(GameMinProjection.class);
            when(game2.getId()).thenReturn(2L);
            when(game2.getTitle()).thenReturn("Game 2");

            GameMinProjection game3 = mock(GameMinProjection.class);
            when(game3.getId()).thenReturn(3L);
            when(game3.getTitle()).thenReturn("Game 3");

            List<GameMinProjection> mockCategoryList = new ArrayList<>();
            mockCategoryList.add(game1);
            mockCategoryList.add(game2);
            mockCategoryList.add(game3);

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockCategoryList);

            // Act
            gameCategoryService.move(validCategoryId, 0, 2);

            // Assert
            // Verifica que a posição do jogo foi atualizada corretamente
            verify(gameCategoryRepository, times(3)).updateBelongingPosition(eq(validCategoryId), anyLong(), anyInt());

            // Verify que a posição foi movida corretamente
            verify(gameCategoryRepository, times(1)).existsById(validCategoryId);
            verify(gameRepository, times(1)).searchByList(validCategoryId);
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando nenhum id de categoria for encontrada")
        void deveRetornarEntityNotFoundQuandoNenhumCategoryIdForEncontrado() {
            // Arrange
            Long invalidCategoryId = 1000L;
            when(gameCategoryRepository.existsById(invalidCategoryId)).thenReturn(false);

            // Act & Assert
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
                gameCategoryService.move(invalidCategoryId, 0, 1);
            });

            // Assert
            assertEquals("Category with ID " + invalidCategoryId + " not found.", ex.getMessage());

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(invalidCategoryId);
        }

        @Test
        @DisplayName("Deve retornar IllegalArgumentException quando os índices forem inválidos")
        void deveRetornarIllegalArgumentExceptionQuandoIndicesForemInvalido() {
            // Arrange
            Long validCategoryId = 1L;

            // Mock de GameMinProjection
            GameMinProjection game1 = mock(GameMinProjection.class);
            when(game1.getId()).thenReturn(1L);
            when(game1.getTitle()).thenReturn("Game 1");

            GameMinProjection game2 = mock(GameMinProjection.class);
            when(game2.getId()).thenReturn(2L);
            when(game2.getTitle()).thenReturn("Game 2");

            GameMinProjection game3 = mock(GameMinProjection.class);
            when(game3.getId()).thenReturn(3L);
            when(game3.getTitle()).thenReturn("Game 3");

            List<GameMinProjection> mockCategoryList = new ArrayList<>();

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockCategoryList);

            // Act & Assert
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                gameCategoryService.move(validCategoryId, -1, 5);
            });

            // Assert
            Assertions.assertTrue(ex.getMessage().contains("Invalid indices"));

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(validCategoryId);
            verify(gameRepository, times(1)).searchByList(validCategoryId);
        }
    }

    @Nested
    @DisplayName("Game Tests")
    class GameTests {

        @Test
        @DisplayName("Deve retornar uma lista de GameMinDTO quando o repositório retornar uma lista de Game")
        void deveRetornarListaDeGameMinDTOQuandoRepositorioRetornarUmGame() {
            // Act
            List<GameMinDTO> result = gameService.findAll();

            // Assert
            assertEquals(1, result.size());
            assertEquals("Game 1", result.get(0).getTitle());
            assertEquals(1L, result.get(0).getId());

            // Verify
            verify(gameRepository, times(1)).findAll();
        }
    }
}