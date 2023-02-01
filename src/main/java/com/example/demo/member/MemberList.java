package com.example.demo.member;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.MemberEntity;

public class MemberList {
    private List<MemberEntity> memberList = new ArrayList<>();

    MemberList(List<MemberEntity> memberList){
        this.memberList = memberList;
    }


    public List<String> getMemberNameList(){
        List<String> memberNameList = new ArrayList<>();
        this.memberList
        .stream()
        .forEach((MemberEntity memberEntity) -> {
            memberNameList.add(memberEntity.getName());
        });
        return memberNameList;
    }
}
