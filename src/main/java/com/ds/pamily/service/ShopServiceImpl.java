package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ShopCateDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.QShop;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopCate;
import com.ds.pamily.entity.ShopImage;
import com.ds.pamily.repository.ShopImageRepository;
import com.ds.pamily.repository.ShopRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{

    private final ShopRepository shopRepository;
    private final ShopImageRepository shopImageRepository;

    @Override
    public List<ShopDTO> getShop() {
        List<Shop> result = shopRepository.findAll();
        return result.stream().map(shop -> shopEntityToDTO(shop)).collect(Collectors.toList());
    }

    @Override
    public Long shopRegister(ShopDTO shopDTO) {
        Map<String, Object> entityMap = shopDtoToEntity(shopDTO);
        Shop shop = (Shop) entityMap.get("shop");
        List<ShopImage> shopImageList = (List<ShopImage>) entityMap.get("shopImgList");
        shopRepository.save(shop);
        log.info(">>>"+shopImageList);
        shopImageList.forEach(shopImage -> {
            shopImageRepository.save(shopImage);
        });
        return shop.getSid();
    }

    @Override
    public PageResultDTO<ShopDTO, Shop> getShopList(PageRequestDTO dto) {
        Pageable pageable = dto.getPageable(Sort.by("sid").descending());
        //JPA가 처리된 결과인 Page<Entity> 객체 생성
        BooleanBuilder booleanBuilder = getShopSearch(dto);
        Page<Shop> result = shopRepository.findAll(booleanBuilder, pageable);
        //JPA로부터 처리된 결과에 Entity를 DTO로 변형하는 처리부분
        Function<Shop, ShopDTO> fn = (entity -> shopEntityToDTO(entity));
        //위에서 만든 2가지를 PageResultDTO에 넣으면 fn에 정의된 대로 변환해서 결과 돌려줌
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ShopDTO shopRead(Long sid) {
        Optional<Shop> result = shopRepository.findById(sid);
        return result.isPresent()?shopEntityToDTO(result.get()):null;
    }

    @Override
    public void shopRemove(Long sid) {
        shopRepository.deleteById(sid);
    }

    @Override
    public void shopModify(ShopDTO dto) {
        Optional<Shop> result = shopRepository.findById(dto.getSid());
        if (result.isPresent()) {
            Shop entity = result.get();
            entity.changeShopTitle(dto.getTitle());
            entity.changeShopTitle(dto.getContent());
            shopRepository.save(entity);
        }
    }

    private BooleanBuilder getShopSearch(PageRequestDTO dto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QShop qShop = QShop.shop;

        String type = dto.getType();
        String keyWord = dto.getKeyword();

        BooleanExpression expression = qShop.sid.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qShop.title.contains(keyWord));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qShop.content.contains(keyWord));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}