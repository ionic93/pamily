package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.RelationDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Relation;
import com.ds.pamily.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    private final RelationRepository relationRepository;

    public int check(Member member1, Member member2) {
        if (member1.equals(member2)) {return 0;}
        for (Relation relation: member2.getFollowing()) {
            if(relation.getFollower().equals(member1)) {return 1;}
        } return -1;
    }

    public void relation(Member member1, Member member2) {
        Relation relation = new Relation();
        log.info("relation : " + relation);
        relation.setFollower(member2);
        relation.setFollowing(member1);

        log.info(relation.getFollower());
        log.info(relation.getFollowing());
        relationRepository.save(relation);
    }

    public void unRelation(Member member1, Member member2) {
        List<Relation> relations = member1.getFollowing();
        log.info("realtions : " + relations);
        for(Relation relation: relations) {
            if(relation.getFollower().equals(member2)) {
                relations.remove(relation);
                relationRepository.delete(relation);
                return;
            }
        }
    }

    @Override
    public PageResultDTO<RelationDTO, Relation> getFollowList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("rid").descending());
        Page<Relation> result = relationRepository.findAll(pageable);
        Function<Relation, RelationDTO> fn = (entity -> entityToDTO(entity));
        log.info("result:" + result);
        return new PageResultDTO<>(result, fn);
    }
}
