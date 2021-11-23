package com.ds.pamily.service;

import com.ds.pamily.dto.*;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Relation;

import java.util.List;

public interface RelationService {
    int check(Member member1, Member member2);

    default Relation dtoToEntity(RelationDTO relationDTO) {
        Relation relation = Relation.builder()
                .rid(relationDTO.getRid())
                .following(relationDTO.getFollowing())
                .follower(relationDTO.getFollower())
                .build();
        return relation;
    }

    default RelationDTO entityToDTO(Relation relation) {
        RelationDTO relationDTO = RelationDTO.builder()
                .rid((relation.getRid()))
                .following(relation.getFollowing())
                .follower(relation.getFollower())
                .build();
        return relationDTO;
    }



    void follow(Member member1, Member member2);

    void unfollow(Member member1, Member member2);

    PageResultDTO<RelationDTO, Relation> getFollowList(PageRequestDTO requestDTO);

    default MemberDTO entityToDTO2(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .name(member.getName())
                .build();
        return memberDTO;
    }

    PageResultDTO<MemberDTO, Member> getFriendList(PageRequestDTO pageRequestDTO);

    List<Relation> getAll();
}
