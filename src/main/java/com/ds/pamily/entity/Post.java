package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeContent(String content) {this.content = content;}

}
