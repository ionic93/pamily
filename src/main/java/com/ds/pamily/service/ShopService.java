package com.ds.pamily.service;

import com.ds.pamily.dto.ShopDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Shop;

public interface ShopService {
    Long shopRegister(ShopDTO shopDTO);

}
