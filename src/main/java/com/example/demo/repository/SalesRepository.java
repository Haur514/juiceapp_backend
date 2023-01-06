package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.SalesEntity;

public interface SalesRepository extends JpaRepository<SalesEntity, Integer>{

    @Query(value="""
            SELECT
                *
            FROM
                sales
            WHERE
                date = ?2
            AND
                userId = ?1
            ;
            """,nativeQuery = true)
    public SalesEntity findByUserIdAndDate(String userId,String date);
}
