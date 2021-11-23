package com.ds.pamily.service;

import com.ds.pamily.dto.MemberDTO;
import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.RelationDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.QMember;
import com.ds.pamily.entity.Relation;
import com.ds.pamily.repository.MemberRepository;
import com.ds.pamily.repository.RelationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final RelationRepository relationRepository;

    public int check(Member member1, Member member2) {
        if (member1.equals(member2)) {return 0;}
        for (Relation relation: member2.getFollowing()) {
            if(relation.getFollower().equals(member1)) {return 1;}
        } return -1;
    }

    public void follow(Member follower, Member following) {
        Relation relation = new Relation();
        log.info("relation : " + relation);
        relation.setFollower(follower);
        relation.setFollowing(following);

        log.info(relation.getFollower());
        log.info(relation.getFollowing());
        relationRepository.save(relation);
    }

    public void unfollow(Member follower, Member following) {
        List<Relation> relations = following.getFollowing();
        log.info("realtions : " + relations);
        for(Relation relation: relations) {
            if(relation.getFollower().equals(follower)) {
                relations.remove(relation);
                relationRepository.delete(relation);
                return;
            }
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QMember qMember = QMember.member;
        String keyword = pageRequestDTO.getKeyword();
        BooleanExpression expression = qMember.mid.gt(0L);
        booleanBuilder.and(expression);
        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")) {
            conditionBuilder.or(qMember.name.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    public PageResultDTO<MemberDTO, Member> getFriendList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mid").descending());
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Member> result = memberRepository.findAll(booleanBuilder, pageable);
        Function<Member, MemberDTO> fn = (entity -> entityToDTO2(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<RelationDTO, Relation> getFollowList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("rid").descending());
        Page<Relation> result = relationRepository.findAll(pageable);
        Function<Relation, RelationDTO> fn = (entity -> entityToDTO(entity));
        log.info("result:" + result);
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public List<Relation> getAll() {
        return relationRepository.findAll();
    }
}
