package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.entity.Frendship;
import com.github.yumyum.chat.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrendshipRepository extends JpaRepository<Frendship, Integer> {

    @Query("SELECT COUNT(fs) > 0  FROM Frendship fs WHERE fs.memberId1 := memberId1 AND fs.memberId2 := memberId2")
    boolean existsByMemberIds(@Param("memberId1") Integer memberId1, @Param("memberId2") Integer memberId2);

//    List<Frendship> findByMember1MemberIdAndMember2MemberId(Integer memberId1, Integer memberId2);
}
