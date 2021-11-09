package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ItemCate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long icno;

    private String cateName;

    @ManyToOne
    private ItemType itno;
}
