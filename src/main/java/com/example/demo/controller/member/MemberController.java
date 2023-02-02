package com.example.demo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.member.requestbody.MemberAddRequestBody;
import com.example.demo.controller.member.requestbody.MemberDeleteBody;
import com.example.demo.controller.member.requestbody.MemberSetActivityBody;
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

    @PostMapping("/member/delete")
    @ResponseBody
    public String deleteMember(
            @RequestBody MemberDeleteBody memberDeleteBody) {
        return memberService.deleteMember(memberDeleteBody.name);
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

    @PostMapping("/member/setactivity")
    @ResponseBody
     public String setMemberActivity(
        @RequestBody MemberSetActivityBody memberSetActivityBody){
        MemberEntity memberEntity= memberService.findByName(memberSetActivityBody.name);
        memberEntity.setActive(memberSetActivityBody.activity);
        memberRepository.save(memberEntity);
        return "success";
     }
}