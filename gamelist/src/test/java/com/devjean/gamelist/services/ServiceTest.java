package com.devjean.gamelist.services;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.commons.IllegalArgumentException;
import com.devjean.gamelist.application.web.dto.GameCategoryDTO;
import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.factory.MockFactory;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests of services")
class ServiceTest {

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

        when(gameRepository.findById(1L)).thenReturn(Optional.of(mockGame));
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
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
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
            GameMinProjection game1 = MockFactory.createGameMinProjection(1L, "Game 1");
            GameMinProjection game2 = MockFactory.createGameMinProjection(2L, "Game 2");
            GameMinProjection game3 = MockFactory.createGameMinProjection(3L, "Game 3");

            List<GameMinProjection> mockProjections = new ArrayList<>(List.of(game1, game2, game3));

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockProjections);

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

            // Act
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
                gameCategoryService.move(invalidCategoryId, 0, 1);
            });

            // Assert
            assertEquals("Category with ID " + invalidCategoryId + " not found.", ex.getMessage());

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(invalidCategoryId);
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando o jogo for nulo")
        void deveRetornarEntityNotFoundExceptionQuandoJogoNulo() {
            // Arrange
            Long validCategoryId = 1L;

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(null);

            // Act
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,() -> {
                gameCategoryService.move(validCategoryId, 0, 1);
            });

            // Assert
            assertEquals("No games found for category with ID " + validCategoryId, ex.getMessage());

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(validCategoryId);
            verify(gameRepository, times(1)).searchByList(validCategoryId);
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando os índices são inválidos")
        void deveRetornarIllegalArgumentExceptionQuandoIndicesInvalidos() {
            // Arrange
            Long validCategoryId = 2L;

            // Mock de GameMinProjection
            GameMinProjection game1 = MockFactory.createGameMinProjection(1L, "Game 1");
            GameMinProjection game2 = MockFactory.createGameMinProjection(2L, "Game 2");
            GameMinProjection game3 = MockFactory.createGameMinProjection(3L, "Game 3");

            List<GameMinProjection> mockProjections = List.of(game1, game2, game3);

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockProjections);

            int invalidSourceIndex = -1;
            int invalidDestinationIndex = 5;

            // Act - sourceIndex Invalido
            IllegalArgumentException exceptionSource = assertThrows(IllegalArgumentException.class, () -> {
                gameCategoryService.move(validCategoryId, invalidSourceIndex, 1);
            });

            // Assert - exceptionSource
            assertTrue(exceptionSource.getMessage().contains("sourceIndex out of bounds: " + invalidSourceIndex));

            IllegalArgumentException exceptionDestination = assertThrows(IllegalArgumentException.class, () -> {
                gameCategoryService.move(validCategoryId,  0, invalidDestinationIndex);
            });

            // Assert - exceptionDestination
            assertTrue(exceptionDestination.getMessage().contains("destinationIndex out of bounds: " + invalidDestinationIndex));

            // Verify
            verify(gameCategoryRepository, times(2)).existsById(validCategoryId);
            verify(gameRepository, times(2)).searchByList(validCategoryId);
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

        @Test
        @DisplayName("Deve retornar IllegalArgumentException quando os índices forem inválidos")
        void deveRetornarIllegalArgumentExceptionQuandoIndicesForemInvalido() {
            // Arrange
            Long validCategoryId = 1L;

            // Mock de GameMinProjection
            GameMinProjection game1 = MockFactory.createGameMinProjection(1L, "Game 1");
            GameMinProjection game2 = MockFactory.createGameMinProjection(2L, "Game 2");
            GameMinProjection game3 = MockFactory.createGameMinProjection(3L, "Game 3");

            List<GameMinProjection> mockProjections = List.of(game1, game2, game3);

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockProjections);

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

        @Test
        @DisplayName("Deve retornar um GameDTO convertido quando buscar por id")
        void deveRetornarGameDTOQuandoBuscarPorId() {

            // Arrange
            Long gameId = 1L;

            // Act
            GameDTO result = gameService.findById(gameId);

            // Assert
            assertEquals(gameId, result.getId());
            assertEquals("Game 1", result.getTitle());
            assertEquals(2024, result.getYear());
            assertEquals("Esporte", result.getGenre());
            assertEquals("XBox", result.getPlatforms());
            assertEquals(4.9, result.getScore());
            assertEquals("https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/9.png", result.getImgUrl());
            assertEquals("This is a game", result.getShortDescription());
            assertEquals("Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi", result.getLongDescription());

            // Verify
            verify(gameRepository, times(1)).findById(gameId);
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando id do Game não existir")
        void deveRetornarEntityNotFoundExceptionQuandoIdDoGameNaoExistir() {
            // Arrange
            Long invalidGameId = 100L;
            when(gameRepository.findById(invalidGameId)).thenReturn(Optional.empty());

            // Act
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
                gameService.findById(invalidGameId);
            });

            // Assert
            assertEquals("Game with ID: " + invalidGameId + " not found.", ex.getMessage());

            // Verify
            verify(gameRepository, times(1)).findById(invalidGameId);
        }

        @Test
        @DisplayName("Deve retornar uma Lista de GameMinDTO quando existir um categoryId")
        void deveRetornarGameMinDTOQuandoExistirUmCategoryId() {
            // Arrange
            Long validCategoryId = 1L;

            // Mock de GameMinProjection
            GameMinProjection game1 = MockFactory.createGameMinProjection(1L, "Game 1");
            GameMinProjection game2 = MockFactory.createGameMinProjection(2L, "Game 2");
            GameMinProjection game3 = MockFactory.createGameMinProjection(3L, "Game 3");

            List<GameMinProjection> mockProjections = List.of(game1, game2, game3);

            when(gameCategoryRepository.existsById(validCategoryId)).thenReturn(true);
            when(gameRepository.searchByList(validCategoryId)).thenReturn(mockProjections);

            // Act
            List<GameMinDTO> result = gameService.findByCategory(validCategoryId);

            // Assert
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals("Game 1", result.get(0).getTitle());
            assertEquals("Game 2", result.get(1).getTitle());
            assertEquals("Game 3", result.get(2).getTitle());

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(validCategoryId);
            verify(gameRepository, times(1)).searchByList(validCategoryId);
        }

        @Test
        @DisplayName("Deve retornar EntityNotFoundException quando a categoria não existir")
        void deveLancarEntityNotFoundExceptionQuandoCategoriaNaoExistir() {
            // Arrange
            Long invalidCategoryId = 1000L;
            when(gameCategoryRepository.existsById(invalidCategoryId)).thenReturn(false);

            // Act
            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
                gameService.findByCategory(invalidCategoryId);
            });

            // Assert
            assertEquals("Category with ID: " + invalidCategoryId + " not found.", ex.getMessage());

            // Verify
            verify(gameCategoryRepository, times(1)).existsById(invalidCategoryId);
            verify(gameRepository, never()).searchByList(anyLong());
        }
    }
}