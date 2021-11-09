package com.ds.pamily.dto;

import com.ds.pamily.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long pid;
    private String content;

    private Long mid;

    @Builder.Default
    //기본값
    private List<PostImageDTO> imageDTOList = new ArrayList<>();

    //댓글수 jpa의 count()
    private int replyCnt;

    private long likesCount;
    private boolean likeState;
    private List<Reply> replyList;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
