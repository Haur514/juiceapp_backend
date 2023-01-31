package com.example.demo.member;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.MemberEntity;

public class MemberList {
    private List<MemberEntity> memberList = new ArrayList<>();

    MemberList(List<MemberEntity> memberList){
        this.memberList = memberList;
    }
}
