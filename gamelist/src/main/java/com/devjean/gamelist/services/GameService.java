package com.devjean.gamelist.services;

import com.devjean.gamelist.entities.Game;
import com.devjean.gamelist.dto.GameDTO;
import com.devjean.gamelist.dto.GameMinDTO;
import com.devjean.gamelist.projections.GameMinProjection;
import com.devjean.gamelist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Game result = repository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        return convertToDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Game> result = repository.findAll();
        return result.stream()
                .map(GameMinDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByCategory(Long categoryId) {
        List<GameMinProjection> result = repository.searchByList(categoryId);
        return result.stream()
                .map(this::convertToMinDTO)
                .collect(Collectors.toList());
    }
}
