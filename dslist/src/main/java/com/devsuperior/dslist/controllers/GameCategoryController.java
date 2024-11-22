package com.devsuperior.dslist.controllers;

import com.devsuperior.dslist.controllers.implement.MoveCategoryCommand;
import com.devsuperior.dslist.controllers.interfaces.Command;
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
