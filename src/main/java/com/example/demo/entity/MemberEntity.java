package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="member")
public class MemberEntity {
    
    @Id
    @Column(name="name")
    private String name;

    @Column(name="displayName")
    private String displayName;

    @Column(name="unpayedAmount")
    private int umpayedAmount;
}
