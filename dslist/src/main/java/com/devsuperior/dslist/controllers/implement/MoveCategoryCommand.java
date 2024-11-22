package com.devsuperior.dslist.controllers.implement;

import com.devsuperior.dslist.controllers.interfaces.Command;
import com.devsuperior.dslist.services.GameCategoryService;
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
