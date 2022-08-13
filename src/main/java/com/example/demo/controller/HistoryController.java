package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.HistoryEntity;
import com.example.demo.service.HistoryService;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @PostMapping
    @RequestMapping("/history/add")
    public String insertHistory(
        @RequestParam("name") String name,
        @RequestParam("item") String item,
        @RequestParam("price") String price
    ){
        int p = Integer.parseInt(price);
        return historyService.insertHistory(name,item,p);
    }

    @PostMapping
    @RequestMapping("/history/delete")
    public String cancelHistory(@RequestParam("name") String name,@RequestParam("id") String id){
        return historyService.removeHistory(Integer.parseInt(id), name);
    }

     @RequestMapping("/history")
     public String getHistory(@RequestParam(name = "name", defaultValue="") String name){
        List<HistoryEntity> historyList;
        Gson gson = new Gson();
        if(name.equals("")){
            historyList = historyService.findAllHistory();
        }else{
            historyList = historyService.findByName(name);
        }
        return gson.toJson(historyList);
     }
}