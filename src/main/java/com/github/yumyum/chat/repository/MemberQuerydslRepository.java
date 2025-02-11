package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.dto.MemberDto;
import com.github.yumyum.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.yumyum.chat.entity.QFriendship.friendship;
import static com.github.yumyum.member.entity.QMember.member;
import static com.querydsl.core.types.ExpressionUtils.any;

@Repository
public class MemberQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public List<MemberDto> findByMemberId1(int memberId) {
        return queryFactory
                .select(Projections.bean(MemberDto.class, member.id, member.loginId, member.memberName))
                .from(member)
                .where(member.id.eq(any(
                        JPAExpressions.select(friendship.member2.id)
                                .from(friendship)
                                .where(friendship.member1.id.eq(memberId))
                )))
                .fetch();
    }

    @Transactional
    public List<MemberDto> findByUsernameContainingOrEmailContaining(String keyword) {
        return queryFactory
                .select(Projections.bean(MemberDto.class, member.id, member.loginId, member.memberName))
                .from(member)
                .where(
                        member.memberName.contains(keyword)
                    .or(member.loginId.contains(keyword))
                )
                .fetch();
    }


}
