package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "post")
public class PostImage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
