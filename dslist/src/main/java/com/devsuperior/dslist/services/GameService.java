package com.devsuperior.dslist.services;

import com.devsuperior.dslist.Entities.Game;
import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public List<GameDTO> findAll() {
        List<Game> result = repository.findAll();
        return result.stream().map(GameDTO::new).toList();
    }
}
