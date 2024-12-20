package com.devjean.gamelist.application.web.controllers;

import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.services.GameService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public GameDTO findById(@Valid @PathVariable Long id) {
        log.info("[FIND-BY-ID]-[Controller] Starting find by id to Games: [{}]", id);
        return service.findById(id);
    }

    @GetMapping
    public List<GameMinDTO> findAll() {
        log.info("[FIND-ALL]-[Controller] Starting find all to Games");
        return service.findAll();
    }

    @PostMapping
    public GameDTO createGame(@RequestBody @Valid GameDTO gameDTO) {
        log.info("[CREATE-GAME]-[Controller] Starting creation of a new game: {}", gameDTO);
        return service.createGame(gameDTO);
    }

    @PostMapping("/{gameId}/category/{categoryId}")
    public GameDTO addGameToCategory(@PathVariable Long gameId, @PathVariable Long categoryId) {
        log.info("[ADD-GAME-TO-CATEGORY]-[Controller] Adding game with ID [{}] to category with ID [{}]", gameId, categoryId);
        return  service.addGameToCategory(gameId, categoryId);
    }

}
