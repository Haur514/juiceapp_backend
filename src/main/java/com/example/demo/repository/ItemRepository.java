package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer>{
    public List<ItemEntity> findByGrouping(String grouping);
    public void deleteByName(String name);
}
