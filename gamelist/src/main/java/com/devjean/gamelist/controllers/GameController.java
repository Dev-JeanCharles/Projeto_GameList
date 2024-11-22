package com.devjean.gamelist.controllers;

import com.devjean.gamelist.dto.GameDTO;
import com.devjean.gamelist.dto.GameMinDTO;
import com.devjean.gamelist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public GameDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<GameMinDTO> findAll() {
        return service.findAll();
    }


}
