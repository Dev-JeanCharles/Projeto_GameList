package com.devsuperior.dslist.services;

import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;
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
