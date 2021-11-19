package com.ds.pamily.controller;

import com.ds.pamily.dto.ShopReplyDTO;
import com.ds.pamily.service.ShopReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/reviews")
@Log4j2
@RequiredArgsConstructor
public class ShopReplyController {
    private final ShopReplyService service;

    @GetMapping("/{sid}/all")
    //@PathVariable = url에서 변수를 처리할때 사용
    public ResponseEntity<List<ShopReplyDTO>> getShopReplyList(@PathVariable("sid") Long sid) {
        log.info("----------------------list-----------------------");
        log.info("Sid: "+sid);

        List<ShopReplyDTO> shopReplyDTOList = service.getListOfShop(sid);
        return new ResponseEntity<>(shopReplyDTOList, HttpStatus.OK);
    }

    @PostMapping("/{sid}")
    //@RequestBody = JSON(application/json) 형태의 HTTP Body 내용을 Java Object로 변환시켜주는 역할
    //즉 register()에 필요한 데이터를 json형태로 받아서 이것을 자바 객체로 변환시켜 준다.
    public ResponseEntity<Long> addShopReply(@RequestBody ShopReplyDTO shopReplyDTO) {
        log.info("-------------------add ShopReply---------------------");
        log.info("shopReplyDTO: "+shopReplyDTO);

        Long srid = service.shopReplyRegister(shopReplyDTO);
        return new ResponseEntity<>(srid, HttpStatus.OK);
    }

    @PutMapping("/{sid}/{srid}")
    public ResponseEntity<Long> modifyShopReply(@PathVariable Long srid, @RequestBody ShopReplyDTO shopReplyDTO) {
        log.info("-------------------modify ShopReply---------------------");
        log.info("shopReplyDTO: "+shopReplyDTO);

        service.shopReplyModify(shopReplyDTO);
        return new ResponseEntity<>(srid, HttpStatus.OK);
    }
    @DeleteMapping("/{sid}/{srid}")
    public ResponseEntity<Long> removeShopReply(@PathVariable Long srid) {
        log.info("-------------------modify removeShopReply---------------------");
        log.info("srid: "+srid);

        service.shopReplyRemove(srid);
        return new ResponseEntity<>(srid, HttpStatus.OK);
    }
}
