package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.QShop;
import com.ds.pamily.entity.Shop;
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

import javax.transaction.Transactional;
import java.util.Arrays;
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
        return shop.getSid();
    }

    @Override
    public PageResultDTO<ShopDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sid").descending());

        Page<Object[]> result = shopRepository.getShopListPage(pageable);
        log.info("result: "+result);
        log.info("requestDTO: "+requestDTO);

        Function<Object[], ShopDTO> fn = (arr -> entitiesToDTO(
                (Shop) arr[0],
                (List<ShopImage>)(Arrays.asList((ShopImage)arr[1])),
                (Long) arr[2])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ShopDTO read(Long sid) {
        Optional<Shop> result = shopRepository.findById(sid);
        log.info("result>>>>>>>>>>>>"+result);
        return result.isPresent()?shopEntityToDTO(result.get()):null;
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
