package com.devjean.gamelist.repositories;

import com.devjean.gamelist.entities.GameCategory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE tb_belonging SET position = :newPosition WHERE category_id = :categoryId AND game_id = :gameId")
    void updateBelongingPosition(Long categoryId, Long gameId, Integer newPosition);

    boolean existsById(@NonNull Long id);
}
