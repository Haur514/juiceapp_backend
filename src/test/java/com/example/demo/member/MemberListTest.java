package com.example.demo.member;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.MemberEntity;

@SpringBootTest
public class MemberListTest {
    private List<MemberEntity> memberEntity;
    // 下処理
    @BeforeEach
    public void preprocess(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("h-yosiok");
        memberEntity.setDisplayName("Yoshioka");
        memberEntity.setUmpayedAmount(10000);
        memberEntity.setAttribute("m1");
        memberEntity.setActive(true);

    }
}
