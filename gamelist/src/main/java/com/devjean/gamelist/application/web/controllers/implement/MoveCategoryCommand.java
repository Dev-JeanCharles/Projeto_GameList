package com.devjean.gamelist.application.web.controllers.implement;

import com.devjean.gamelist.application.web.controllers.interfaces.Command;
import com.devjean.gamelist.services.GameCategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MoveCategoryCommand implements Command {

    private GameCategoryService service;
    private Long categoryId;
    private int sourceIndex;
    private int destinationIndex;

    @Override
    public void execute() {
        service.move(categoryId, sourceIndex, destinationIndex);
    }
}
