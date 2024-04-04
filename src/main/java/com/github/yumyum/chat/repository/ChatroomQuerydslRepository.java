package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.entity.*;
import com.github.yumyum.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.github.yumyum.chat.entity.QChatroom.chatroom;
import static com.github.yumyum.member.entity.QMember.member;
import static com.github.yumyum.chat.entity.QMemberChatroom.memberChatroom;

@Slf4j
@Repository
public class ChatroomQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ChatroomQuerydslRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public Chatroom save(Chatroom chatroom){
        em.persist(chatroom);
        return chatroom;
    }

    @Transactional
    public MemberChatroom save(MemberChatroom memberChatroom){
        em.persist(memberChatroom);
        return memberChatroom;
    }

    @Transactional
    public ChatContent save(ChatContent chatContent){
        log.info("chatContent: {}", chatContent);
        em.persist(chatContent);
        return chatContent;
    }

    @Transactional
    public void saveChatroom(ChatroomDto chatroomDto) throws IOException {
        if (chatroomDto.getMemberIds().isEmpty() ||
                chatroomDto.getTitle().isEmpty()){
            log.error("chatroomDto: {}", chatroomDto);
            throw new RuntimeException("chatroomDto null 에러");
        }

        Chatroom chatroom = Chatroom.builder()
                .title(chatroomDto.getTitle())
                .build();

        // profile: null (채팅방 기본 이미지)
        if (chatroomDto.getProfile() != null) {
            chatroom.setProfile(chatroomDto.getProfile().getBytes());
        }

        int chatroomId = save(chatroom).getChatroomId();
        log.info("chatroomId: {}", chatroomId);

        for (Integer memberId : chatroomDto.getMemberIds()) {
            MemberChatroom memberChatroom = MemberChatroom.builder()
                    .chatroom(Chatroom.builder().chatroomId(chatroomId).build())
                    .member(Member.builder().id(memberId).build())
                    .build();
            save(memberChatroom);
        }
    }
    
    public List<MemberDto> getChatroomMembers(Integer chatroomId) {
        return queryFactory
                .select(Projections.bean(MemberDto.class, member.id, member.loginId, member.memberName))
                .from(member)
                .join(member.memberChatrooms, memberChatroom).fetchJoin()
                .where(memberChatroom.chatroom.chatroomId.eq(chatroomId))
                .fetch();
    }

    @Transactional
    public void deleteMemberChatroom(LeaveChatDto leaveChatDto) {
        queryFactory
            .delete(memberChatroom)
            .where(memberChatroom.member.id.eq(leaveChatDto.getMemberId()),
                    memberChatroom.chatroom.chatroomId.eq(leaveChatDto.getChatroomId()))
            .execute();

    }

    @Transactional
    public void updateChatroom(Integer chatroomId, ChatroomUpdateDto chatroomUpdateDto) throws IOException {
        JPAUpdateClause updateClause = queryFactory.update(chatroom);

        if (chatroomUpdateDto.getProfile().getBytes() != null) {
            updateClause.set(chatroom.profile, chatroomUpdateDto.getProfile().getBytes());
        }

        updateClause
            .set(chatroom.title, chatroomUpdateDto.getTitle())
            .where(chatroom.chatroomId.eq(chatroomId))
            .execute();
    }
}
