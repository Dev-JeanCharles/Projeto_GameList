package com.devjean.gamelist.services;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.commons.IllegalArgumentException;
import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.application.web.dto.GameCategoryDTO;
import com.devjean.gamelist.projections.GameMinProjection;
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
public class GameCategoryService {

    private final GameCategoryRepository gameCategoryRepository;
    private final GameRepository gameRepository;

    private static final String CATEGORIES_NOT_FOUND = "No categories found.";
    private static final String CATEGORY_ID_NOT_FOUND = "Category with ID %d not found.";
    private static final String GAME_NOT_FOUND = "No games found for category with ID %d";

    @Autowired
    public GameCategoryService(GameCategoryRepository gameCategoryRepository, GameRepository gameRepository) {
        this.gameCategoryRepository = gameCategoryRepository;
        this.gameRepository = gameRepository;
    }

    private GameCategoryDTO convertToDTO(GameCategory entity) {
        return new GameCategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<GameCategoryDTO> findAllCategories() {
        log.info("[FIND-ALL]-[Service] Starting find all to Category:");

        List<GameCategory> result = gameCategoryRepository.findAll();

        if (result.isEmpty()) {
            throw new EntityNotFoundException(CATEGORIES_NOT_FOUND);
        }

        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void move(Long categoryId, int sourceIndex, int destinationIndex) {
        log.info("[MOVE]-[Service] Starting operation move Game in Category 1 or 2: {}, [initial: {}, destination: {}]", categoryId, sourceIndex, destinationIndex);

        if (!gameCategoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(String.format(CATEGORY_ID_NOT_FOUND, categoryId));
        }

        List<GameMinProjection> game = gameRepository.searchByList(categoryId);

        if (game == null || game.isEmpty()) {
            throw new EntityNotFoundException(String.format(GAME_NOT_FOUND, categoryId));
        }

        if (sourceIndex < 0 || sourceIndex >= game.size() || destinationIndex < 0 || destinationIndex > game.size()) {
            StringBuilder errorMessage = new StringBuilder("Invalid indices:");
            if (sourceIndex < 0 || sourceIndex >= game.size()) {
                errorMessage.append(" sourceIndex out of bounds: ").append(sourceIndex);
            }
            if (destinationIndex < 0 || destinationIndex > game.size()) {
                errorMessage.append(" destinationIndex out of bounds: ").append(destinationIndex);
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        GameMinProjection obj = game.remove(sourceIndex);

        game.add(destinationIndex, obj);

        int min = Math.min(sourceIndex, destinationIndex);
        int max = Math.max(sourceIndex, destinationIndex);

        for (int i = min; i <= max; i++) {
            gameCategoryRepository.updateBelongingPosition(categoryId, game.get(i).getId(), i);
        }
    }
}
