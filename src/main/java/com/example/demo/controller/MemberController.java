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
    @RequestMapping("/member/add")
    public String addMember(
        @RequestParam(name="name") String name,
        @RequestParam(name="displayName") String displayName,
        @RequestParam(name="attribute") String attribute
    ){
        return memberService.addMember(name,displayName,attribute);
    }

    @PostMapping
    @RequestMapping("/member/update")
    public String updateMember(
        @RequestParam(name="name") String name,
        @RequestParam(name="displayName",defaultValue="") String displayName,
        @RequestParam(name="unpayedAmount",defaultValue="") String unpayedAmount,
        @RequestParam(name="attribute",defaultValue="") String attribute
    ){
        return memberService.updateMember(name,displayName,unpayedAmount,attribute);
    }

    @PostMapping
    @RequestMapping("/member/delete")
    public String deleteMember(
        @RequestParam("name") String name
    ){
        return memberService.deleteMember(name);
    }

    // @PostMapping
    // @RequestMapping("/setUnpayedAmount")
    // public String setUnpayedAmount(@RequestParam("name") String name,@RequestParam("unpayedAmount") String unpayedAmount){
    //     return memberService.setUnpayedAmount(name, Integer.parseInt(unpayedAmount));
    // }

     @RequestMapping("/member/find")
     public String getActiveMembers(
        @RequestParam(name="name",defaultValue="") String name,
        @RequestParam(name="attribute",defaultValue="") String attribute
     ){
        return memberService.findMembers(name,attribute);
     }
}