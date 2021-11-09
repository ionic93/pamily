package com.ds.pamily.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data //getter / setter / 기본생성자가 자동으로 들어감
public class PageRequestDTO { //Get 방식으로 요청된 파라미터 처리
    private int page, size;
    private String type, keyword;

    public PageRequestDTO(){ //기본 값
        page = 1;
        size = 10;
    }
    //기본값이 1이므로 page -1을 넣어서 0부터 시작하도록 처리
    public Pageable getPageable(Sort sort){ //목록 처리, -1은 페이지가 0부터 시작하므로
        return PageRequest.of(page -1, size, sort);
    }
}
