package com.ds.pamily.entity;

import com.ds.pamily.dto.PostImageDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid;

    @Column(length = 100,  unique = true)
    private String email;

    private String password;

    @Column(length = 30 )
    private String name;

    @Column(length = 30)
    private String mobile;

    private boolean fromSocial;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Relation> following;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Relation> followers;


    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }

    public void changeEmail(String email) {
        this.email = email;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changeName(String name) {
        this.name = name;
    }
    public void changeMobile(String mobile) {
        this.mobile = mobile;
    }
}
