package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ChatService;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping
    @RequestMapping("/chat/add")
    public String insertHistory(
        @RequestParam("message") String message
    ){
        chatService.insertChat(message);
        return "success";
    }

    @PostMapping
    @RequestMapping("/chat/delete")
    public String cancelHistory(@RequestParam("name") String name,@RequestParam("id") String id){
        return "hoge";
        // return chatService.removeHistory(Integer.parseInt(id), name);
    }

     @RequestMapping("/chat")
     public String getChat(){
        return new Gson().toJson(chatService.findAllChat());
     }
}
