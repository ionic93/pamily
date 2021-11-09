package com.ds.pamily.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> { //페이지 결과 처리
    //DTO와 EN(Entity)은 다양한 클래스에서 사용 할수 있게 타입을 지정해놓음

    //DTO리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;

    //Function<En, DTO>는 엔티티 객체들을 DTO로 변환해주는 기능
    //Page<EN> result = JpaRepository에서 나온 결과, Function<EN, DTO> fn = 엔티티를 dto로 바꿀수 있는 로직
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        //result에서 나온 엔티티 객체들을 맵핑해서 전부 dto로 바꾸고 리스트로 처리
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        page = pageable.getPageNumber() +1 ; //페이지가 0부터 시작하기때문에 view단에서 1로 보임
        size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0))*10;

        start = tempEnd -9; //페이징이 10개이므로 끝페이지 -9

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;
        //페이지번호를 담는 리스트
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
