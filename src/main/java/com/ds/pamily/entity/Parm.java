package com.ds.pamily.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Parm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fno;

    private String fname;

//    private Long point;
//    private Long itemCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parm")
    private List<ParmImage> parmImageList = new ArrayList<>();

    public void addParmImage(ParmImage parmImage) {
        parmImage.setPuid(this.parmImageList.size());
        parmImage.setParm(this);
        parmImageList.add(parmImage);
    }
}
