package com.devjean.gamelist.services;

import com.devjean.gamelist.entities.GameCategory;
import com.devjean.gamelist.dto.GameCategoryDTO;
import com.devjean.gamelist.projections.GameMinProjection;
import com.devjean.gamelist.repositories.GameCategoryRepository;
import com.devjean.gamelist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameCategoryService {

    @Autowired
    private GameCategoryRepository gameCategoryRepository;

    @Autowired
    private GameRepository gameRepository;

    private GameCategoryDTO convertToDTO(GameCategory entity) {
        return new GameCategoryDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<GameCategoryDTO> findAll() {
        List<GameCategory> result = gameCategoryRepository.findAll();
        return result.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void move(Long categoryId, int sourceIndex, int destinationIndex) {

        List<GameMinProjection> category = gameRepository.searchByList(categoryId);

        GameMinProjection obj = category.remove(sourceIndex);
        category.add(destinationIndex, obj);

        int min = Math.min(sourceIndex, destinationIndex);
        int max = Math.max(sourceIndex, destinationIndex);

        for (int i = min; i <= max; i++) {
            gameCategoryRepository.updateBelongingPosition(categoryId, category.get(i).getId(), i);
        }
    }
}
