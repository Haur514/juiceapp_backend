package com.example.demo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.HistoryService;
import com.example.demo.service.MemberService;

@RestController
@EnableAutoConfiguration
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HistoryService historyService;

    @PostMapping("/member/add")
    @ResponseBody
    public String addMember(
            @RequestBody MemberAddRequestBody memberAddRequestBody) {
        return memberService.addMember(
            memberAddRequestBody.name, 
            memberAddRequestBody.displayName, 
            memberAddRequestBody.attribute);
    }

    @PostMapping
    @RequestMapping("/member/update")
    public String updateMember(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "displayName", defaultValue = "") String displayName,
            @RequestParam(name = "unpayedAmount", defaultValue = "") String unpayedAmount,
            @RequestParam(name = "attribute", defaultValue = "") String attribute) {
        return memberService.updateMember(name, displayName, unpayedAmount, attribute);
    }

    @PostMapping
    @RequestMapping("/member/delete")
    public String deleteMember(
            @RequestParam("name") String name) {
        return memberService.deleteMember(name);
    }

    @RequestMapping("/member")
    public String getActiveMembers(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "attribute", defaultValue = "") String attribute) {
        return memberService.findMembers(name, attribute);
    }

    @RequestMapping("/member/ranking")
    public String getMemberRanking() {
        return memberService.getMemberRanking();
    }

    @RequestMapping("/member/unpayed")
     public String getMemberWithUnpayedAmount(){
        return memberService.getMemberWithUnpayedAmount();
     }

     @PostMapping
    @RequestMapping("/member/setactivity")
     public String setMemberActivity(
        @RequestParam(name="name") String name,
        @RequestParam(name="activity") boolean activity){
        MemberEntity memberEntity= memberService.findByName(name);
        memberEntity.setActive(activity);
        memberRepository.save(memberEntity);
        return "success";
     }
}