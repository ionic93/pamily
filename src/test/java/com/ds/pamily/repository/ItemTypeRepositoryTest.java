package com.ds.pamily.repository;

import com.ds.pamily.entity.ItemType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemTypeRepositoryTest {

    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Test
    public void insertType() {
        IntStream.rangeClosed(1,4).forEach(i->{
            ItemType itemType = ItemType.builder()
                    .typeName("동물")
                    .build();
            itemTypeRepository.save(itemType);
        });
    }
}