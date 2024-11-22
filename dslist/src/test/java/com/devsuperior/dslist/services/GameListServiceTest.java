package com.devsuperior.dslist.services;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.mocks.GameListMock;
import com.devsuperior.dslist.repositories.GameListRepository;
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
class GameListServiceTest {

    @Mock
    private GameListRepository gameListRepository;

    @InjectMocks
    private GameListService gameListService;

    @BeforeEach
    void setUp() {
        GameList mockGame = GameListMock.createValidGame();
        List<GameList> mockGameList = List.of(mockGame);

        when(gameListRepository.findAll()).thenReturn(mockGameList);
    }

    @Test
    @DisplayName("Deve retornar uma lista de GameListDTO quando o repositório retornar uma lista de GameList")
    void deveReturnarListaDeGameDTOQuandoRepositorioRetornarUmGameList() {
        List<GameListDTO> result = gameListService.findAll();

        assertEquals(1, result.size());
        assertEquals("Game 1", result.get(0).getName());
        assertEquals(1L, result.get(0).getId());

        verify(gameListRepository, times(1)).findAll();
    }
}