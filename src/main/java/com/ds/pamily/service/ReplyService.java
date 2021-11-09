package com.ds.pamily.service;

import com.ds.pamily.dto.ReplyDTO;
import com.ds.pamily.entity.Member;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Reply;

import java.util.List;

public interface ReplyService {
    //게시물의 모든 댓글을 가져옴
    List<ReplyDTO> getListOfPost(Long pid);

    //게시물 댓글 추가
    Long register(ReplyDTO postReplyDTO);

    //특정한 게시물 댓글 수정
    void modify(ReplyDTO postReplyDTO);

    //게시물 댓글 삭제
    void remove(Long rid);

    default Reply dtoToEntity(ReplyDTO postReplyDTO) {
        Reply postReply = Reply.builder()
                .rid(postReplyDTO.getRid())
                .post(Post.builder().pid(postReplyDTO.getPid()).build())
                .member(Member.builder().mid(postReplyDTO.getMid()).build())
                .text(postReplyDTO.getText())
                .build();
        return postReply;
    }

    default ReplyDTO entityToDTO(Reply postReply) {
        ReplyDTO postReplyDTO = ReplyDTO.builder()
                .rid(postReply.getRid())
                .pid(postReply.getPost().getPid())
                .mid(postReply.getMember().getMid())
                .name(postReply.getMember().getName())
                .email(postReply.getMember().getEmail())
                .text(postReply.getText())
                .regDate(postReply.getRegDate())
                .modDate(postReply.getModDate())
                .build();

        return postReplyDTO;
    }
}
