package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ParmDTO;
import com.ds.pamily.entity.Parm;

public interface ParmService {
    Long register(ParmDTO dto);

    PageResultDTO<ParmDTO, Parm> getList(PageRequestDTO requestDTO);

    default Parm dtoToEntity(ParmDTO dto) {
        Parm entity = Parm.builder()
                .fno(dto.getFno())
                .fname(dto.getFname())
                .point(dto.getPoint())
                .build();
        return entity;
    }

    default ParmDTO entityToDTO(Parm entity) {
        ParmDTO dto = ParmDTO.builder()
                .fno(entity.getFno())
                .fname(entity.getFname())
                .point(entity.getPoint())
                .itemCnt(entity.getItemCnt())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
