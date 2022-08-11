package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer>{


    @Query(value="""
        SELECT
            *
        FROM
            (
                SELECT
                    *
                FROM
                    history
                WHERE
                    name = ?1
                ORDER BY
                    date
                desc limit 30
            ) as A
        ORDER BY date
        ;
    """,
        nativeQuery = true)
    public List<HistoryEntity> findByName(String name);
}
