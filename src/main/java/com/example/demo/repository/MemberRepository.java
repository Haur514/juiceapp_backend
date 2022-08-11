package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{

    @Query(value="""
        SELECT
            *
        FROM
            member
        WHERE
            name = ?1
        ;
    """,
        nativeQuery = true)
    public List<MemberEntity> findByName(String name);
}
