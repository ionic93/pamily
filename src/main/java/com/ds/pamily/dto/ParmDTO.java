package com.ds.pamily.dto;

import com.ds.pamily.entity.ParmImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParmDTO {

    private Long fno;
    private String fname;
//    private Long point;

    @Builder.Default
    private List<ParmImageDTO> imageDTOList = new ArrayList<>();

//    private Long itemCnt;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
