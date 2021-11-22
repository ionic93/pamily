package com.ds.pamily.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "member")
public class Relation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @ManyToOne
    private Member name;

    @JsonBackReference
    @ManyToOne
    private Member following;

    @ManyToOne
    private Member follower;

}
