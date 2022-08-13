package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ItemEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.HistoryService;
import com.example.demo.service.ItemService;
import com.example.demo.service.MemberService;

@RestController
@EnableAutoConfiguration
public class PurchaseController {
    @Autowired HistoryService historyService;
    @Autowired ItemService itemService;
    @Autowired MemberService memberService;

    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;


    @RequestMapping("/purchase")
    public String purchaseItem(
        @RequestParam("name") String name,
        @RequestParam("item") String item
    ){
        ItemEntity purchasedItem = itemRepository.findByName(item);
        if(purchasedItem == null){
            return "failed";
        }
        MemberEntity purchasedMember = memberRepository.findByName(name);
        if(purchasedMember == null){
            return "failed";
        }
        int price = purchasedItem.getSellingPrice();
        
        historyService.insertHistory(name,item,price);
        itemService.purchased(item);
        memberService.purchased(name,price);
        return "success";
    }
}
