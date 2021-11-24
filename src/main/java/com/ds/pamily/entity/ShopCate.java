package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ShopCate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scno;

    private String cateName;

}