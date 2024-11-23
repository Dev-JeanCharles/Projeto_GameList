package com.devjean.gamelist.application.web.controllers;

import com.devjean.gamelist.application.web.dto.GameDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public GameDTO findById(@PathVariable Long id) {
        log.info("[FIND-BY-ID]-[Controller] Starting find by id to Games: [{}]", id);
        return service.findById(id);
    }

    @GetMapping
    public List<GameMinDTO> findAll() {
        log.info("[FIND-ALL]-[Controller] Starting find all to Games");
        return service.findAll();
    }
}
