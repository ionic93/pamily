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
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itno;

    private String typeName;
}