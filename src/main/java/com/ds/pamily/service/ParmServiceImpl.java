package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ParmDTO;
import com.ds.pamily.entity.Parm;
import com.ds.pamily.entity.ParmImage;
import com.ds.pamily.repository.ParmImageRepository;
import com.ds.pamily.repository.ParmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ParmServiceImpl implements ParmService {

//    private final ParmRepository parmRepository;

//    @Override
//    public Long register(ParmDTO parmDTO) {
//        Parm parm = dtoToEntity(parmDTO);
//        parmRepository.save(parm);
//        return parm.getFno();
//    }

//    @Override
//    public PageResultDTO<ParmDTO, Parm> getList(PageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("fno").descending());
//        Page<Parm> result = parmRepository.findAll(pageable);
//        Function<Parm, ParmDTO> fn = (entity -> entityToDTO(entity));
//
//        return new PageResultDTO<>(result, fn);
//    }
}
