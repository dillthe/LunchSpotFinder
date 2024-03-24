package com.github.yumyum.chat.repository;

import com.github.yumyum.chat.dto.ChatroomDto;
import com.github.yumyum.chat.entity.Chatroom;
import com.github.yumyum.chat.entity.Member;
import com.github.yumyum.chat.entity.MemberChatroom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.github.yumyum.chat.entity.QChatroom.chatroom;

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

//    @Transactional
//    public void saveChatroom(ChatroomProfileImg chatroomProfileImgDto) {
//        if (chatroomProfileImgDto.getMemberIds().isEmpty() ||
//                chatroomProfileImgDto.getProfile() == null ||
//                chatroomProfileImgDto.getTitle().isEmpty()){
//            log.error("chatroomProfileImgDto: {}", chatroomProfileImgDto);
//            throw new RuntimeException("saveChatroom null 에러");
//        }
////        em.createQuery("INSERT INTO Chatroom (title, profile) VALUES (:title, :profile)")
////                .setParameter("title", chatroomProfileImgDto.getTitle())
////                .setParameter("profile", chatroomProfileImgDto.getProfile())
////                .executeUpdate();
//        Chatroom chatroom = Chatroom.builder()
//                .title(chatroomProfileImgDto.getTitle())
//                .profile(chatroomProfileImgDto.getProfile())
//                .build();
//        int chatroomId = save(chatroom).getChatroomId();
//        log.info("chatroomId: {}", chatroomId);
//
//        for (Integer memberId : chatroomProfileImgDto.getMemberIds()) {
//            MemberChatroom memberChatroom = MemberChatroom.builder()
//                    .chatroomId(chatroomId)
//                    .memberId(memberId)
//                    .build();
//            save(memberChatroom);
//        }
//    }

}
