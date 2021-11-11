package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ino;

    @ManyToOne
    private ItemCate icno;

    private String itemName;
    private String itemFileName;
    private String itemInfo;

}
