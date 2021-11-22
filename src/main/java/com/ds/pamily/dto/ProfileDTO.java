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
public class ProfileDTO {

    private Long profileId;

    private Long mid;

    @Builder.Default
    //기본값
    private List<ProfileImageDTO> profileImageDTOList = new ArrayList<>();

    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
