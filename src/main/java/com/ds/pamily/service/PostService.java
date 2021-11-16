package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.PostDTO;
import com.ds.pamily.dto.PostImageDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.PostImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PostService {
    Long register(PostDTO postDTO);

    //영화 조회 페이지
    PostDTO getPost(Long pid);

    //목록 처리
    PageResultDTO<PostDTO, Object[]> getList(PageRequestDTO requestDTO);
    

    default Map<String, Object> dtoToEntity(PostDTO postDTO){ // Map = key, value
        // movie와 movieImage를 같이 처리하기 위해 Map 사용
        Map<String, Object> entityMap = new HashMap<>();

        Post post = Post.builder()
                .pid(postDTO.getPid())
                .member(Member.builder().mid(postDTO.getMid()).build())
                .content(postDTO.getContent())
                .build();
        //"movie"
        entityMap.put("post",post);

        List<PostImageDTO> imageDTOList = postDTO.getImageDTOList();

        //MovieImageDTO처리
        if (imageDTOList !=null && imageDTOList.size() > 0){
            List<PostImage> postImageList = imageDTOList.stream()
                    .map(postImageDTO -> {
                        PostImage postImage = PostImage.builder()
                                .path(postImageDTO.getPath())
                                .imgName(postImageDTO.getImgName())
                                .uuid(postImageDTO.getUuid())
                                .post(post)
                                .build();
                        return postImage;
                    }).collect(Collectors.toList());
            //"imgList"
            entityMap.put("imgList", postImageList);
        }
        return entityMap;
    }

    default PostDTO entitiesToDTO(Post post, List<PostImage> postImages, Long replyCnt){
        PostDTO postDTO = PostDTO.builder()
                .pid(post.getPid())
                .content(post.getContent())
                .mid(post.getMember().getMid())
                .regDate(post.getRegDate())
                .modDate(post.getModDate())
                .build();

        List<PostImageDTO> postImageDTOList = postImages.stream()
                .map(postImage -> {
                    return PostImageDTO.builder().imgName(postImage.getImgName())
                            .path(postImage.getPath())
                            .uuid(postImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        postDTO.setImageDTOList(postImageDTOList);
        postDTO.setReplyCnt(replyCnt.intValue());

        return postDTO;
    }
}
