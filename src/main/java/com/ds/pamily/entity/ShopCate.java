package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ShopCate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scno;

    private String cateName;
}