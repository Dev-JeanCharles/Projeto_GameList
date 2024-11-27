package com.devjean.gamelist.repositories;

import com.devjean.gamelist.entities.Belonging;
import com.devjean.gamelist.entities.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BelongingRepository extends JpaRepository<Belonging, Long> {

    @Query("SELECT MAX(b.position) FROM Belonging b WHERE b.id.gameCategory = :category")
    Optional<Integer> findMaxPositionByCategory(GameCategory category);
}
