package com.example.demo.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.ManipulateMemberList;
import com.example.demo.entity.MemberEntity;
import com.example.demo.repository.MemberRepository;
import com.google.gson.Gson;

@Service
@Transactional
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public List<MemberEntity> getAllMembers(){
        return memberRepository.findAll();
    }

    public String findMembers(String name,String attribute){
        List<MemberEntity> memberEntityList = findMembersByAttribute(attribute);
        if(!name.equals("")){
            memberEntityList = memberEntityList.stream()
                .filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());
        }
        return new Gson().toJson(memberEntityList);
    }

    public List<MemberEntity> findMembersByAttribute(String attribute){
        if(attribute.equals("")){
            return memberRepository.findAll();
        }else{
            return memberRepository.findByAttribute(attribute);
        }
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

    public String updateMember(
        String name,
        String displayName,
        String unpayedAmount,
        String attribute
    ){
        if(memberRepository.findByName(name).isEmpty()){
            return "failed";
        }
        MemberEntity memberEntity = memberRepository.findByName(name).get(0);
        String tmp_displayName = displayName.equals("") ? memberEntity.getDisplayName() : displayName;
        int tmp_unpayedAmount = unpayedAmount.equals("") ? memberEntity.getUmpayedAmount() : Integer.parseInt(unpayedAmount);
        String tmp_attribute = attribute.equals("") ? memberEntity.getAttribute() : attribute;
        memberEntity.setDisplayName(tmp_displayName);
        memberEntity.setUmpayedAmount(tmp_unpayedAmount);
        memberEntity.setAttribute(tmp_attribute);
        memberRepository.save(memberEntity);
        return "success";
    }

    public String addMember(String name,String displayName,String attribute){
        // 既に登録済みかチェック
        if(!memberRepository.findByName(name).isEmpty()){
            return "failed";
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setDisplayName(displayName);
        memberEntity.setUmpayedAmount(0);
        memberEntity.setAttribute(attribute);
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

    public String getMemberRanking(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        return new ManipulateMemberList().getMembersRanking(memberEntityList);
    }


}
