package com.ds.pamily.repository;

import com.ds.pamily.entity.PostImage;
import com.ds.pamily.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByProfileProfileId(Long profileId);

}
