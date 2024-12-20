package com.devjean.gamelist.application.web.controllers;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.controllers.implement.MoveCategoryCommand;
import com.devjean.gamelist.application.web.controllers.interfaces.Command;
import com.devjean.gamelist.application.web.dto.GameCategoryDTO;
import com.devjean.gamelist.application.web.dto.GameMinDTO;
import com.devjean.gamelist.application.web.dto.ReplacementDTO;
import com.devjean.gamelist.services.GameCategoryService;
import com.devjean.gamelist.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public List<GameCategoryDTO> findAllCategories() {
        log.info("[FIND-ALL]-[Controller] Starting find all to Category");

        return gameCategoryService.findAllCategories();
    }

    @GetMapping("/{categoryId}/games")
    public List<GameMinDTO> findByCategoryGames(@PathVariable Long categoryId) {
        log.info("[FIND-BY-CATEGORY-GAMES]-[Controller] Starting find all to Category by Games: {}", categoryId);

        return gameService.findByCategory(categoryId);
    }

    @PostMapping("/{categoryId}/replacement")
    public void move(@PathVariable Long categoryId, @RequestBody ReplacementDTO body) {
        log.info("[MOVE]-[Controller] Starting operation move Game in Category 1 or 2: {}", categoryId);

        Command moveCommand = new MoveCategoryCommand(gameCategoryService, categoryId, body.getSourceIndex(), body.getDestinationIndex());
        moveCommand.execute();
    }
}
