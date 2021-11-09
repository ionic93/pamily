package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Parm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fno;

    @Column(length = 100, nullable = false)
    private String fname;

    private Long point;
    private Long itemCnt;
}
