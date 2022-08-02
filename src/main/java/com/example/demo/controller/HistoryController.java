package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DatabaseOperation;
import com.example.demo.service.HistoryService;

@RestController
@EnableAutoConfiguration
public class HistoryController {

     @GetMapping("/history")
     public String getParam(
         @RequestParam(value="user") String user
        //  @RequestParam(value="less") String less,
        //  @RequestParam(value="latest") String latest
     ){
         try {
            new HistoryService().insertHistory((long)1,"h-yosiok","A",80,new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse("2022/07/25 01:11:13"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return new DatabaseOperation().getHistory(user).toString();
     }
}