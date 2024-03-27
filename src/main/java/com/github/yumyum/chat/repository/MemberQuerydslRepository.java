package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.entity.Member;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.yumyum.chat.entity.QFriendship.friendship;
import static com.github.yumyum.chat.entity.QMember.member;
import static com.querydsl.core.types.ExpressionUtils.any;

@Repository
public class MemberQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

//    public List<Member> findByMemberId1(Integer memberId1) {
//        return queryFactory
//                .selectFrom(member)
//                .leftJoin(member.friendships1, friendship)
//                .on(member.memberId.eq(friendship.friendshipId))
//                .fetch();
//    }
    @Transactional
    public List<Member>findByMemberId1(int memberId) {
        return queryFactory
//                .select(member.memberId, member.email, member.name)
                .select(member)
                .from(member)
                .where(member.memberId.eq(any(
                        JPAExpressions.select(friendship.member2.memberId)
                                .from(friendship)
                                .where(friendship.member1.memberId.eq(memberId))
                )))
                .fetch();
    }

    @Transactional
    public List<Member> findByUsernameContainingOrEmailContaining(String keyword) {
        return queryFactory
                .selectFrom(member)
                .where(
                        member.name.contains(keyword)
                    .or(member.email.contains(keyword))
                )
                .fetch();
    }


}
