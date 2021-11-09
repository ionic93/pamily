package com.ds.pamily.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude="parm")
public class ParmImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pinum;

    private String puid;

    private String pimgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parm parm;
}
