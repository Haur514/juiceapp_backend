package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ItemService;

@RestController
@EnableAutoConfiguration
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping("/item")
    public String getItemList(
       @RequestParam(name="grouping",defaultValue="") String grouping
    ){
       return itemService.getItemList(grouping);
    }

    @PostMapping
    @RequestMapping("/item/add")
    public String addItemList(
        @RequestParam("name") String name,
        @RequestParam("sellingprice") String sellingPrice,
        @RequestParam("costprice") String costPrice,
        @RequestParam("grouping") String grouping
    ){
        int int_sellingPrice = Integer.parseInt(sellingPrice);
        int int_costPrice = Integer.parseInt(costPrice);
        return itemService.addItem(name,int_sellingPrice,int_costPrice,grouping);
    }

    @PostMapping
    @RequestMapping("/item/delete")
    public String deleteItem(
        @RequestParam("name") String name
    ){
        return itemService.deleteItem(name);
    }


}