package com.devsuperior.dslist.services;

import com.devsuperior.dslist.entities.GameCategory;
import com.devsuperior.dslist.dto.GameCategoryDTO;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameCategoryRepository;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameCategoryService {

    @Autowired
    private GameCategoryRepository gameCategoryRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameCategoryDTO> findAll() {
        List<GameCategory> result = gameCategoryRepository.findAll();
        return result.stream().map(GameCategoryDTO::new).toList();
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
