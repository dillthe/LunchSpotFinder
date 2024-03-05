package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
}
