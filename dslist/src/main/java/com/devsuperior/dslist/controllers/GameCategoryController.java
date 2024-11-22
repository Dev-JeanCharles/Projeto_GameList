package com.devsuperior.dslist.controllers;

import com.devsuperior.dslist.dto.GameCategoryDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.dto.ReplacementDTO;
import com.devsuperior.dslist.services.GameCategoryService;
import com.devsuperior.dslist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class GameCategoryController {

    @Autowired
    private GameCategoryService gameCategoryService;

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameCategoryDTO> findAll() {
        return gameCategoryService.findAll();
    }

    @GetMapping(value = "/{categoryId}/games")
    public List<GameMinDTO> findByCategoryGames(@PathVariable Long categoryId) {
        return gameService.findByCategory(categoryId);
    }

    @PostMapping(value = "/{categoryId}/replacement")
    public void move(@PathVariable Long categoryId, @RequestBody ReplacementDTO body) {
        gameCategoryService.move(categoryId, body.getSourceIndex(), body.getDestinationIndex());
    }
}
