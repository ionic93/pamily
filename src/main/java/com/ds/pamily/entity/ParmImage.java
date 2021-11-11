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

    // ParmImage 순번
    private int puid;
    private String piname;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parm parm;

    public void setPuid(int puid) {this.puid = puid;}
    public void setParm(Parm parm) {this.parm = parm;}
}
