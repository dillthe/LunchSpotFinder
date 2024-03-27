package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findAll();

    @Query(value = "SELECT COUNT(fs.friendship_id) > 0 FROM friendship fs WHERE fs.member_id1 = :memberId1 AND fs.member_id2 = :memberId2", nativeQuery = true)
    int existsFriendship(@Param("memberId1") int memberId1, @Param("memberId2") int memberId2);

//    List<Friendship> findAllById(int memberId1);
//    @Modifying
//    @Query(value = "DELETE FROM friendship fs WHERE fs.member_id1 = :memberId1 AND fs.member_id2 = :memberId2", nativeQuery = true)
//    void deleteFriendship(@Param("memberId1") int memberId1, @Param("memberId2") int memberId2);


    @Query(value = "SELECT * FROM friendship WHERE friendship.member_id1 = :memberId1 AND friendship.member_id2 = :memberId2", nativeQuery = true)
    List<Friendship> findByMemberId1AndMemberId2(@Param("memberId1") int memberId1, @Param("memberId2") int memberId2);

//    @Query("SELECT COUNT(fs.friendship_id) > 0 FROM friendship fs WHERE fs.member_id1 = :id1 AND fs.member_id2 = :id2")
//    boolean existsByIds(@Param("id1") int id1, @Param("id2") int id2);

//    List<Friendship> findByMember1MemberIdAndMember2MemberId(Integer memberId1, Integer memberId2);

//    @Query(value = "SELECT COUNT(fs) > 0 FROM friendship fs WHERE fs.member_id1 = :memberId1")
//    boolean existsByMemberId1(int memberId1);
}
