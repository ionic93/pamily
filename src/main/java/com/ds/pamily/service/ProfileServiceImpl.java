package com.ds.pamily.service;

import com.ds.pamily.dto.PageRequestDTO;
import com.ds.pamily.dto.PageResultDTO;
import com.ds.pamily.dto.ProfileDTO;
import com.ds.pamily.entity.Profile;
import com.ds.pamily.entity.ProfileImage;
import com.ds.pamily.repository.ProfileImageRepository;
import com.ds.pamily.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;
    private final ProfileImageRepository profileImageRepository;

    @Transactional //2개의 테이블을 동시에 사용하므로
    @Override
    public Long register(ProfileDTO profileDTO) {
        log.info("profileDTO>>>>"+profileDTO);
        Map<String, Object> entityMap = dtoToEntity(profileDTO);
        Profile profile = (Profile) entityMap.get("profile"); //movie라고 이름지어진 Map을 호출
        log.info("profile>>>>"+profile);
        List<ProfileImage> profileImageList = (List<ProfileImage>) entityMap.get("imgList");
        log.info("profileImageList>>>>"+profileImageList);

        profileRepository.save(profile);
        profileImageList.forEach(postImage -> {
            profileImageRepository.save(postImage);
        });
        return profile.getProfileId(); //등록하고 원래 페이지로 돌아가기 위해
    }

    @Override
    public PageResultDTO<ProfileDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("profileId").descending());

        Page<Object[]> result = profileRepository.getListPage(pageable);
        log.info("result >>>>>>>>" + result);
        Function<Object[], ProfileDTO> fn = new Function<Object[], ProfileDTO>() {

            @Override
            public ProfileDTO apply(Object[] arr) {
                log.info("arr>"+arr[0]);
                log.info("arr>"+arr[1]);
                ProfileDTO dto = entitiesToDTO(
                        (Profile) arr[0],
                        (List<ProfileImage>)(Arrays.asList((ProfileImage)arr[1]))
                );
                log.info("dto1>"+dto.getProfileId());
                log.info("dto2>"+dto.getProfileImageDTOList());
                return dto;
            }
        };
        return new PageResultDTO<>(result, fn);
    }
}
