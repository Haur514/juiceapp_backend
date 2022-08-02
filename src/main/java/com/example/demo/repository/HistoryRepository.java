package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.History;
import com.example.demo.entity.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity,Integer>{
    History findByUser(String user);
}
