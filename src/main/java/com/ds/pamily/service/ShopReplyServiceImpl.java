package com.ds.pamily.service;

import com.ds.pamily.dto.ShopReplyDTO;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopReply;
import com.ds.pamily.repository.ShopReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShopReplyServiceImpl implements ShopReplyService{
    private final ShopReplyRepository shopReplyRepository;

    @Override
    public List<ShopReplyDTO> getListOfShop(Long sid) {
        Shop shop = Shop.builder().sid(sid).build();
        List<ShopReply> result = shopReplyRepository.findByShop(shop);

        return result.stream().map(shopReply -> entityToDTO(shopReply)).collect(Collectors.toList());
    }

    @Override
    public Long shopReplyRegister(ShopReplyDTO shopReplyDTO) {
        ShopReply shopReply = dtoToEntity(shopReplyDTO);
        shopReplyRepository.save(shopReply);

        return shopReply.getSrid();
    }

    @Override
    public void shopReplyModify(ShopReplyDTO shopReplyDTO) {
        Optional<ShopReply> result = shopReplyRepository.findById(shopReplyDTO.getSrid());

        if (result.isPresent()) {
            ShopReply shopReply = result.get();
            shopReply.changeShopText(shopReplyDTO.getText());

            shopReplyRepository.save(shopReply);
        }
    }

    @Override
    public void shopReplyRemove(Long srid) {
        shopReplyRepository.deleteById(srid);
    }
}
