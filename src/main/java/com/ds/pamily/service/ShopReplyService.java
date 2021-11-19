package com.ds.pamily.service;

import com.ds.pamily.dto.ShopReplyDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Shop;
import com.ds.pamily.entity.ShopReply;

import java.util.List;

public interface ShopReplyService {
    //모든 댓글
    List<ShopReplyDTO> getListOfShop(Long sid);

    //댓글 추가
    Long shopReplyRegister(ShopReplyDTO shopReplyDTO);

    //댓글 수정
    void shopReplyModify(ShopReplyDTO shopReplyDTO);

    //댓글 삭제
    void shopReplyRemove(Long srid);

    default ShopReply dtoToEntity(ShopReplyDTO shopReplyDTO) {
        ShopReply shopReply = ShopReply.builder()
                .srid(shopReplyDTO.getSrid())
                .shop(Shop.builder().sid(shopReplyDTO.getSid()).build())
                .member(Member.builder().mid(shopReplyDTO.getMid()).build())
                .text(shopReplyDTO.getText())
                .build();
        return shopReply;
    }

    default ShopReplyDTO entityToDTO(ShopReply shopReply) {
        ShopReplyDTO shopReplyDTO = ShopReplyDTO.builder()
                .srid(shopReply.getSrid())
                .sid(shopReply.getShop().getSid())
                .mid(shopReply.getMember().getMid())
                .name(shopReply.getMember().getName())
                .text(shopReply.getText())
                .regDate(shopReply.getRegDate())
                .modDate(shopReply.getModDate())
                .build();

        return shopReplyDTO;
    }
}
