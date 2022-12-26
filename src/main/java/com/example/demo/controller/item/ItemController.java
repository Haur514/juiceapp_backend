package com.example.demo.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ItemEntity;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;


@RestController
@EnableAutoConfiguration
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    
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

    @PostMapping
    @RequestMapping("/item/update")
    public String updateItem(
        @RequestParam(name="name") String name,
        @RequestParam(name="sellingPrice",defaultValue="") String sellingPrice,
        @RequestParam(name="costPrice",defaultValue="") String costPrice,
        @RequestParam(name="grouping",defaultValue="") String grouping,
        @RequestParam(name="salesFigure",defaultValue="") String salesFigure
    ){
        return itemService.updateItem(name,sellingPrice,costPrice,grouping,salesFigure);
    }


    @PostMapping
    @RequestMapping("/item/ranking")
    public String getItemRanking(){
        return itemService.getItemRanking();
    }

    @PostMapping
    @RequestMapping("/item/setactivity")
     public String setMemberActivity(
        @RequestParam(name="id") String name,
        @RequestParam(name="activity") boolean activity){
        ItemEntity itemEntity= itemService.findByName(name);
        itemEntity.setActive(activity);
        itemRepository.save(itemEntity);
        return "success";
     }

    

}
