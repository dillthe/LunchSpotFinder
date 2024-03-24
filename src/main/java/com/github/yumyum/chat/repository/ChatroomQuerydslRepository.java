package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.dto.ChatroomDto;
import com.github.yumyum.chat.dto.LeaveChatDto;
import com.github.yumyum.chat.entity.Chatroom;
import com.github.yumyum.chat.entity.Member;
import com.github.yumyum.chat.entity.MemberChatroom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.github.yumyum.chat.entity.QMember.member;
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

    public Chatroom save(Chatroom chatroom){
        em.persist(chatroom);
        return chatroom;
    }

    public MemberChatroom save(MemberChatroom memberChatroom){
        em.persist(memberChatroom);
        return memberChatroom;
    }

    // TODO @Transactional 꼭 필요한 부분만 사용하게 추후 메소드 수정
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
                    .member(Member.builder().memberId(memberId).build())
                    .build();
            save(memberChatroom);
        }
    }
    
    public List<Member> getChatroomMembers(Integer chatroomId) {
        return queryFactory
                .selectFrom(member)
                .join(member.memberChatrooms, memberChatroom).fetchJoin()
                .where(memberChatroom.chatroom.chatroomId.eq(chatroomId))
                .fetch();
    }

    @Transactional
    public void deleteMemberChatroom(LeaveChatDto leaveChatDto) {
        queryFactory
            .delete(memberChatroom)
            .where(memberChatroom.member.memberId.eq(leaveChatDto.getMemberId()),
                    memberChatroom.chatroom.chatroomId.eq(leaveChatDto.getChatroomId()))
            .execute();
    }

}
