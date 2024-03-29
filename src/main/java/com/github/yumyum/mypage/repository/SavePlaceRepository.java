package com.github.yumyum.mypage.repository;

import com.github.yumyum.mypage.repository.entity.SavePlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  SavePlaceRepository extends JpaRepository<SavePlaceEntity, Integer> {
    Optional<List<SavePlaceEntity>> findByUserSn(int userSn);

}
