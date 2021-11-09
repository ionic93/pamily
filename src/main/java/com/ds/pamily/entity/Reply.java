package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String text;

    public void changeText(String text) {
        this.text = text;
    }
}
