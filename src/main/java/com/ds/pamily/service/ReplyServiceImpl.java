package com.ds.pamily.service;

import com.ds.pamily.dto.ReplyDTO;
import com.ds.pamily.entity.Post;
import com.ds.pamily.entity.Reply;
import com.ds.pamily.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDTO> getListOfPost(Long pid) {
        Post post = Post.builder().pid(pid).build();
        List<Reply> result = replyRepository.findByPost(post);

        return result.stream().map(postReply -> entityToDTO(postReply)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReplyDTO postReplyDTO) {
        Reply postReply = dtoToEntity(postReplyDTO);
        replyRepository.save(postReply);

        return postReply.getRid();
    }

    @Override
    public void modify(ReplyDTO postReplyDTO) {
        Optional<Reply> result = replyRepository.findById(postReplyDTO.getRid());

        if (result.isPresent()) {
            Reply postReply = result.get();
            postReply.changeText(postReplyDTO.getText());

            replyRepository.save(postReply);
        }
    }

    @Override
    public void remove(Long rid) {
        replyRepository.deleteById(rid);
    }
}
