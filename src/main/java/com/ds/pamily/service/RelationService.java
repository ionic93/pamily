package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.RelationDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Relation;

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

    void relation(Member member1, Member member2);

    void unRelation(Member member1, Member member2);

    PageResultDTO<RelationDTO, Relation> getFollowList(PageRequestDTO requestDTO);

}
