package com.github.yumyum.map.repository;

import com.github.yumyum.map.repository.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Integer> {
}
