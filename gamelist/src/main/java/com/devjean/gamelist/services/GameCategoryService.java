package com.devjean.gamelist.services;

import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.dto.GameCategoryDTO;
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

    @Autowired
    public GameCategoryService(GameCategoryRepository gameCategoryRepository, GameRepository gameRepository) {
        this.gameCategoryRepository = gameCategoryRepository;
        this.gameRepository = gameRepository;
    }

    private GameCategoryDTO convertToDTO(GameCategory entity) {
        return new GameCategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<GameCategoryDTO> findAll() {
        log.info("[FIND-ALL]-[Service] Starting find all to Category:");

        List<GameCategory> result = gameCategoryRepository.findAll();
        log.info("[FIND-ALL]-[Service] List GameCategory saved in result: [{}]", result);

        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void move(Long categoryId, int sourceIndex, int destinationIndex) {
        log.info("[MOVE]-[Service] Starting operation move Game in Category 1 or 2: {}, {}, {}", categoryId, sourceIndex, destinationIndex);

        List<GameMinProjection> category = gameRepository.searchByList(categoryId);
        log.info("[MOVE]-[Service] List GameMinProjection saved in category: [{}]", category);

        GameMinProjection obj = category.remove(sourceIndex);
        log.info("[MOVE]-[Service] Object of GameMinProjection remove first index: [{}]", obj);

        category.add(destinationIndex, obj);

        int min = Math.min(sourceIndex, destinationIndex);
        int max = Math.max(sourceIndex, destinationIndex);
        log.info("[MOVE]-[Service] Positions min and max of Game: {}, {}", min, max);

        for (int i = min; i <= max; i++) {
            gameCategoryRepository.updateBelongingPosition(categoryId, category.get(i).getId(), i);
        }
    }
}