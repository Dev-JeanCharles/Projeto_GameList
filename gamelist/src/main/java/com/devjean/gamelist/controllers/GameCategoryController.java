package com.devjean.gamelist.controllers;

import com.devjean.gamelist.controllers.implement.MoveCategoryCommand;
import com.devjean.gamelist.controllers.interfaces.Command;
import com.devjean.gamelist.dto.GameCategoryDTO;
import com.devjean.gamelist.dto.GameMinDTO;
import com.devjean.gamelist.dto.ReplacementDTO;
import com.devjean.gamelist.services.GameCategoryService;
import com.devjean.gamelist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class GameCategoryController {

    private final GameCategoryService gameCategoryService;
    private final GameService gameService;

    @Autowired
    public GameCategoryController(GameCategoryService gameCategoryService, GameService gameService) {
        this.gameCategoryService = gameCategoryService;
        this.gameService = gameService;
    }

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
        Command moveCommand = new MoveCategoryCommand(gameCategoryService, categoryId, body.getSourceIndex(), body.getDestinationIndex());
        moveCommand.execute();
    }
}
