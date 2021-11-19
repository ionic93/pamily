package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.PostImage;
import com.ds.pamily.repository.MainPostRepository;
import com.ds.pamily.repository.PostImageRepository;
import com.ds.pamily.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Transactional //2개의 테이블을 동시에 사용하므로
    @Override
    public Long register(PostDTO postDTO) {
        log.info("postDTO>>"+postDTO);
        Map<String, Object> entityMap = dtoToEntity(postDTO);
        Post post = (Post) entityMap.get("post"); //movie라고 이름지어진 Map을 호출
        log.info("post>>"+post);
        List<PostImage> postImageList = (List<PostImage>) entityMap.get("imgList");
        log.info(">>"+postImageList);

        postRepository.save(post);
        postImageList.forEach(postImage -> {
            postImageRepository.save(postImage);
        });
        return post.getPid(); //등록하고 원래 페이지로 돌아가기 위해
    }

    @Override
    public PostDTO getPost(Long pid) {
        //Movie와 MovieImage들, 평균 평점과 리뷰 개수 등이 필요함

        List<Object[]> result = postRepository.getPostWithAll(pid);

        //Post 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값
        Post post = (Post) result.get(0)[0];

        //영화의 이미지 개수만큼 PostImage 객체 필요
        List<PostImage> postImageList = new ArrayList<>();

        result.forEach(arr->{
            PostImage postImage = (PostImage) arr[1];
            postImageList.add(postImage);
        });
        Long replyCnt = (Long) result.get(0)[2]; //리뷰 개수 - 모든 Row가 동일한 값

        return entitiesToDTO(post, postImageList, replyCnt);
    }

    @Override
    public PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("pid").descending());

        Page<Object[]> result = postRepository.getListPage(pageable);
        log.info("result >>>>>>>>" + result);
        Function<Object[], PostDTO> fn = new Function<Object[], PostDTO>() {
            @Override
            public PostDTO apply(Object[] arr) {
                log.info("arr>"+arr[0]);
                log.info("arr>"+arr[1]);
                log.info("arr>"+arr[2]);
                PostDTO dto = entitiesToDTO(
                    (Post) arr[0],
                    (List<PostImage>)(Arrays.asList((PostImage)arr[1])),
                    (Long) arr[2]
                );
                log.info("dto1>"+dto.getPid());
                log.info("dto2>"+dto.getContent());
                log.info("dto3>"+dto.getImageDTOList());
                return dto;
            }
        };
//        Function<Object[], PostDTO> fn = (arr -> entitiesToDTO(
//                (Post) arr[0],
//                (List<PostImage>)(Arrays.asList((PostImage)arr[1])),
//                (Long) arr[2])
//        );
        return new PageResultDTO<>(result, fn);
    }

}
