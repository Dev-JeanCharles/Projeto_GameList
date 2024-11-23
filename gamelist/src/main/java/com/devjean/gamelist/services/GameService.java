package com.devjean.gamelist.services;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.projections.GameMinProjection;
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

    @Autowired
    private GameRepository repository;

    private GameDTO convertToDTO(Game entity) {
        return new GameDTO(entity);
    }

    private GameMinDTO convertToMinDTO(GameMinProjection projection) {
        return new GameMinDTO(projection);
    }

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        log.info("[FIND-BY-ID]-[Service] Starting find by id to Game: {}", id);

        Game result = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game not found with id: " + id));

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

        List<GameMinProjection> result = repository.searchByList(categoryId);

        return result.stream()
                .map(this::convertToMinDTO)
                .collect(Collectors.toList());
    }
}
