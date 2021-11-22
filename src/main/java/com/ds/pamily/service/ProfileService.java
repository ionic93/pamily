package com.ds.pamily.service;

import com.ds.pamily.dto.*;
import com.ds.pamily.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProfileService {
    Long register(ProfileDTO profileDTO);

    PageResultDTO<ProfileDTO, Object[]> getList(PageRequestDTO requestDTO);


    default Map<String, Object> dtoToEntity(ProfileDTO profileDTO){ // Map = key, value
        // movie와 movieImage를 같이 처리하기 위해 Map 사용
        Map<String, Object> entityMap = new HashMap<>();

        Profile profile = Profile.builder()
                .profileId(profileDTO.getProfileId())
                .member(Member.builder().mid(profileDTO.getMid()).build())
                .build();
        //"movie"
        entityMap.put("profile",profile);

        List<ProfileImageDTO> profileImageDTOList = profileDTO.getProfileImageDTOList();

        //MovieImageDTO처리
        if (profileImageDTOList !=null && profileImageDTOList.size() > 0){
            List<ProfileImage> profileImageList = profileImageDTOList.stream()
                    .map(profileImageDTO -> {
                        ProfileImage profileImage = ProfileImage.builder()
                                .path(profileImageDTO.getPath())
                                .imgName(profileImageDTO.getImgName())
                                .uuid(profileImageDTO.getUuid())
                                .profile(profile)
                                .build();
                        return profileImage;
                    }).collect(Collectors.toList());
            //"imgList"
            entityMap.put("imgList", profileImageList);
        }
        return entityMap;
    }

    default ProfileDTO entitiesToDTO(Profile profile, List<ProfileImage> profileImages){
        ProfileDTO profileDTO = ProfileDTO.builder()
                .profileId(profile.getProfileId())
                .mid(profile.getMember().getMid())
                .regDate(profile.getRegDate())
                .modDate(profile.getModDate())
                .build();

        List<ProfileImageDTO> profileImageDTOList = profileImages.stream()
                .map(profileImage -> {
                    return ProfileImageDTO.builder().imgName(profileImage.getImgName())
                            .path(profileImage.getPath())
                            .uuid(profileImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        profileDTO.setProfileImageDTOList(profileImageDTOList);
        return profileDTO;
    }


}
