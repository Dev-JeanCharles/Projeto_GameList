package com.devjean.gamelist.services;

import com.devjean.gamelist.application.web.commons.DuplicateTitleException;
import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.commons.ResourceNotFoundException;
import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.entities.Belonging;
import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.projections.GameMinProjection;
import com.devjean.gamelist.repositories.BelongingRepository;
import com.devjean.gamelist.repositories.GameCategoryRepository;
import com.devjean.gamelist.repositories.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

    private final GameRepository repository;

    private final GameCategoryRepository gameCategoryRepository;
    private final BelongingRepository belongingRepository;

    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with ID: %d not found.";
    private static final String GAME_NOT_FOUND = "Game with ID: %d not found.";

    @Autowired
    public GameService(GameRepository repository, GameCategoryRepository gameCategoryRepository, BelongingRepository belongingRepository) {
        this.repository = repository;
        this.gameCategoryRepository = gameCategoryRepository;
        this.belongingRepository = belongingRepository;
    }

    private GameDTO convertToDTO(Game entity) {
        return new GameDTO(entity);
    }

    private GameMinDTO convertToMinDTO(GameMinProjection projection) {
        return new GameMinDTO(projection);
    }

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        log.info("[FIND-BY-ID]-[Service] Starting find by id to Game: {}", id);

        Game result = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(GAME_NOT_FOUND, id)));

        return convertToDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        log.info("[FIND-ALL]-[Service] Starting find all to Game:");

        List<Game> result = repository.findAll();

        return result.stream()
                .map(GameMinDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByCategory(Long categoryId) {
        log.info("[FIND-ALL]-[Service] Starting find by Category: {}", categoryId);

        if (!gameCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_MESSAGE, categoryId));
        }

        List<GameMinProjection> result = repository.searchByList(categoryId);

        return result.stream()
                .map(this::convertToMinDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GameDTO createGame(GameDTO gameDTO) {
        log.info("[CREATE-GAME]-[Service] Validating and saving new Game: {}", gameDTO);

        log.info("[CREATE-GAME]-[Service] Checking if title exists: {}", gameDTO.getTitle());
        if (repository.existsByTitle(gameDTO.getTitle())) {
            log.error("Duplicate title found: {}", gameDTO.getTitle());

            throw new DuplicateTitleException("Game with title '" + gameDTO.getTitle() + "' already exists.");
        }

        Game game = Game.fromDTO(gameDTO);
        Game savedGame = repository.save(game);

        return new GameDTO(savedGame);
    }

    public GameDTO addGameToCategory(Long gameId, Long categoryId) {
        Game game = repository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with ID: " + gameId));

        GameCategory category = gameCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        Integer lastPosition = belongingRepository.findMaxPositionByCategory(category)
                .orElse(0);

        Belonging belonging = new Belonging(game, category, lastPosition + 1);
        belongingRepository.save(belonging);

        return new GameDTO(game);
    }

}
