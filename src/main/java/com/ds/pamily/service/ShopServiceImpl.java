package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.QShop;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopImage;
import com.ds.pamily.repository.ShopImageRepository;
import com.ds.pamily.repository.ShopReplyRepository;
import com.ds.pamily.repository.ShopRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{
    private final ShopRepository shopRepository;
    private final ShopImageRepository shopImageRepository;
    private final ShopReplyRepository shopReplyRepository;

    @Transactional
    @Override
    public Long shopRegister(ShopDTO shopDTO) {
        Map<String, Object> entityMap = dtoToEntity(shopDTO);
        log.info(entityMap);
        Shop shop = (Shop) entityMap.get("shop");
        log.info("HERE1111"+ shop);
        List<ShopImage> shopImageList = (List<ShopImage>) entityMap.get("shopImgList");
        shopRepository.save(shop);
        log.info("HERE~"+shopImageList);
        shopImageList.forEach(shopImage -> {
            shopImageRepository.save(shopImage);
        });
        log.info("HERE~2"+shopImageList);
        return shop.getSid();
    }

    @Transactional //삭제 기능 구현 위해 추가
    @Override
    public void removeWithShopImageAndReply(Long sid) {
        shopReplyRepository.deleteBySid(sid); //댓글부터 먼저 삭제
        shopImageRepository.deleteShopImageBySid(sid); //관련 이미지 삭제
        shopRepository.deleteById(sid); //게시글 삭제
    }

    @Override
    public PageResultDTO<ShopDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sid").descending());

        Page<Object[]> result1 = shopRepository.getShopListPage(pageable);
        log.info("result1: "+result1);
        log.info("requestDTO: "+requestDTO);

        //엔티티를 DTO로 바꿀 로직 선언  = 받아온 엔티티의 배열중 0번째는 Board타입으로, 1번째는 Member타입으로, 2번째는 Long타입으로 변환
        Function<Object[], ShopDTO> fn =
                (arr -> entitiesToDTO((Shop) arr[0], (List<ShopImage>)(Arrays.asList((ShopImage)arr[1])), (Long)arr[2]));

        Page<Object[]> result = shopRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                pageable);

        log.info("serviceSearch: "+result);
        return new PageResultDTO<>(result, fn);
    }

//    @Override
//    public ShopDTO read(Long sid) {
//        Optional<Shop> result = shopRepository.findById(sid);
//        log.info("result>>>>>>>>>>>>"+result);
//        return result.isPresent()?shopEntityToDTO(result.get()):null;
//    }

    @Override
    public ShopDTO getShop(Long sid) {
        //Movie와 MovieImage들, 평균 평점과 리뷰 개수 등이 필요함

        List<Object[]> result = shopRepository.getShopWithAll(sid);

        //Shop 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값
        Shop shop = (Shop) result.get(0)[0];

        //영화의 이미지 개수만큼 MovieImage 객체 필요
        List<ShopImage> shopImageList = new ArrayList<>();

        result.forEach(arr->{
            ShopImage shopImage = (ShopImage) arr[1];
            shopImageList.add(shopImage);
        });

        Long shopReplyCnt = (Long) result.get(0)[2]; //리뷰 개수 - 모든 Row가 동일한 값

        return entitiesToDTO(shop, shopImageList, shopReplyCnt);
    }

    @Override
    public void remove(Long sid) {
        shopRepository.deleteById(sid);
    }

    @Override
    public void modify(ShopDTO shopDTO) {
        Optional<Shop> result = shopRepository.findById(shopDTO.getSid());
        if (result.isPresent()) {
            Shop entity = result.get();
            entity.changeShopTitle(shopDTO.getTitle());
            entity.changeShopContent(shopDTO.getContent());
            shopRepository.save(entity);
        }
    }
}
