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
        try{
            if(memberRepository.findByName(name).isEmpty()){
                return "failed";
            }else{
                memberRepository.deleteByName(name);
                return "success";
            }
        }catch(Exception e){
            return "failed";
        }
    }

    public String addMember(String name,String displayName){
        // 既に登録済みかチェック
        if(!memberRepository.findByName(name).isEmpty()){
            return "failed";
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setDisplayName(displayName);
        memberEntity.setUmpayedAmount(0);
        memberRepository.save(memberEntity);
        return "success";
    }

    public String setUnpayedAmount(String name,int unpayedamount){
        if(memberRepository.findByName(name).isEmpty()){
            return "failed";
        }
        MemberEntity memberEntity = memberRepository.findByName(name).get(0);
        memberEntity.setUmpayedAmount(unpayedamount);
        memberRepository.save(memberEntity);
        return "success";
    }    
}
