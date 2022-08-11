package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public List<MemberEntity> getAllMembers(){
        return memberRepository.findAll();
    }

    public String deleteMember(String name){
        return "success";
    }

    public String addMember(String name,String displayName){
        return "success";
    }
    
}
