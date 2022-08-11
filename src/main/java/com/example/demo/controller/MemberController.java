package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;

@RestController
@EnableAutoConfiguration
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping
    @RequestMapping("/addMember")
    public String insertHistory(@RequestParam("name") String name,@RequestParam("displayName") String displayName){
        return memberService.addMember(name,displayName);
    }

    @PostMapping
    @RequestMapping("/deleteMember")
    public String cacelHistory(@RequestParam("name") String name){
        return memberService.deleteMember(name);
    }

    @PostMapping
    @RequestMapping("/setUnpayedAmount")
    public String setUnpayedAmount(@RequestParam("name") String name,@RequestParam("unpayedAmount") String unpayedAmount){
        return memberService.setUnpayedAmount(name, Integer.parseInt(unpayedAmount));
    }

     @RequestMapping("/activeMembers")
     public String getActiveMembers(){
        return "hoge";
     }
}