package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ItemEntity;
import com.example.demo.repository.ItemRepository;
import com.google.gson.Gson;


@Service
@Transactional
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public String getItemList(String grouping) {
        if(grouping.equals("")){
            return new Gson().toJson(itemRepository.findAll());
        }
        return new Gson().toJson(itemRepository.findByGrouping(grouping));
    }

    public String addItem(
        String name,
        int sellingPrice,
        int costPrice,
        String group
    ){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(name);
        itemEntity.setSellingPrice(sellingPrice);
        itemEntity.setCostPrice(costPrice);
        itemEntity.setGrouping(group);
        itemEntity.setSalesFigure(0);
        itemRepository.save(itemEntity);
        return "success";
    }

    public String deleteItem(String name){
        try{
            itemRepository.deleteByName(name);
            return "success";
        }catch(Exception e){
            return "failed";
        }
    }
}