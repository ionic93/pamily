package com.ds.pamily.dto;

import com.ds.pamily.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationDTO {
    private Long rid;
    private Member name;
    private Member following;
    private Member follower;
}
