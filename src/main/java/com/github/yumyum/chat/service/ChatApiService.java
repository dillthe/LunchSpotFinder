package com.github.yumyum.chat.service;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.entity.*;
import com.github.yumyum.chat.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatApiService {

    private final MemberFriendRepository memberFriendRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatroomQuerydslRepository chatroomQuerydslRepository;
//    private final ChatContentRepository chatContentRepository;

    @Transactional
    public int isUsersFriend(final int memberId1, final int memberId2) {
        return friendshipRepository.existsFriendship(memberId1, memberId2);
    }

//    @Transactional
//    public void makeFreindship(final int memberId1, final int memberId2) {
////        Friendship friendship = friendshipRepository.findByMemberId1AndMemberId2(memberId1, memberId2)
////                .orElseThrow(IllegalArgumentException::new);
////        friendshipRepository.findByMemberId1AndMemberId2(memberId1, memberId2);
//    }

    @Transactional
    public List<Friendship> getFriends() {
        return friendshipRepository.findAll();
    }

    @Transactional
    public List<Member> getMemberAllFriends(int id) {
        return memberQuerydslRepository.findByMemberId1(id);
    }

    /**
     * friendship table에서 row (id1, id2), (id2, id1) 2개씩 저장
     * 참고: https://okky.kr/questions/590874 마지막 댓글 참고)
     * @param memberId1
     * @param memberId2
     */
    @Transactional
    public void makeFriend(final int memberId1, final int memberId2) {
        Friendship friendship1 = Friendship.builder()
                .member1(Member.builder().memberId(memberId1).build())
                .member2(Member.builder().memberId(memberId2).build())
                .build();
        Friendship friendship2 = Friendship.builder()
                .member1(Member.builder().memberId(memberId2).build())
                .member2(Member.builder().memberId(memberId1).build())
                .build();

        friendshipRepository.save(friendship1);
        friendshipRepository.save(friendship2);
    }

    /**
     * friendship table에서 row (id1, id2), (id2, id1) 2개씩 삭제
     * 참고: https://okky.kr/questions/590874 마지막 댓글 참고)
     * @param id1
     * @param id2
     */
    @Transactional
    public void breakFreindship(int id1, int id2) {
        int usersFriend = isUsersFriend(id1, id2);
        log.info("usersFriend: {}", usersFriend);
        if (isUsersFriend(id1, id2) == 0) {
            new RuntimeException(String.format("%s와 %s는 이미 친구가 아닙니다.", id1, id2));
        } else {
            List<Friendship> friendships1 = friendshipRepository.findByMemberId1AndMemberId2(id1, id2);
            List<Friendship> friendships2 = friendshipRepository.findByMemberId1AndMemberId2(id2, id1);

            friendshipRepository.delete(friendships1.get(0));
            friendshipRepository.delete(friendships2.get(0));
            log.info(String.format("%s와 %s 친구 관계 제거 완료", id1, id2));
        }
    }

    public String checkMembersFriendShip(int userId1, int friendShipSearchId) {
        if (userId1 == friendShipSearchId) {
            return String.format("자기 자신과 친구 관계를 맺을 수 없습니다.");
        }

        if (isUsersFriend(userId1, friendShipSearchId) > 0) {
            return String.format("%s와 %s는 이미 친구입니다.", userId1, friendShipSearchId);
        }

        makeFriend(userId1, friendShipSearchId);
        return String.format("%s와 %s는 친구가 되었습니다.", userId1, friendShipSearchId);
    }

    public List<Member> searchMembers(String keyword) {
        return memberQuerydslRepository.findByUsernameContainingOrEmailContaining(keyword);
    }

    public List<Member> getAllMembers() {
        return memberFriendRepository.findAll();
    }

    @Transactional
    public void createChatroom(ChatroomDto chatroomDto) throws IOException {
        chatroomQuerydslRepository.saveChatroom(chatroomDto);
    }

    public List<Member> getChatroomMembers(Integer chatroomId) {
        return chatroomQuerydslRepository.getChatroomMembers(chatroomId);
    }

    public void leaveChatroomMember(LeaveChatDto leaveChatDto) {
        chatroomQuerydslRepository.deleteMemberChatroom(leaveChatDto);
    }

    public void updateChatroom(Integer chatroomId, ChatroomUpdateDto chatroomUpdateDto) throws IOException {
        chatroomQuerydslRepository.updateChatroom(chatroomId, chatroomUpdateDto);
    }

    public void saveChatContent(ChatMessage chatMessage) {
        MessageType chatMessageType = chatMessage.getType();
        if (chatMessageType == MessageType.CHAT_TEXT) {
            String content = chatMessage.getContent();
            Integer memberId = chatMessage.getMemberId();
            Integer roomId = chatMessage.getRoomId();
//            chatContentRepository.saveText(
//                    content,
//                    memberId,
//                    roomId
//            );
            ChatContent chatContent = ChatContent
                    .builder()
                    .text(content)
                    .memberChatroom(
                            MemberChatroom
                                    .builder()
                                    .member(
                                        Member
                                            .builder()
                                            .memberId(memberId)
                                            .build()
                                    )
                                    .chatroom(
                                        Chatroom
                                            .builder()
                                            .chatroomId(roomId)
                                            .build()
                                    )
                                    .build()
                    )
                    .build();
            chatroomQuerydslRepository.save(chatContent);
        } else if (chatMessageType == MessageType.CHAT_IMG) {
            log.info("img");
        } else {
            throw new RuntimeException(String.format("%s는 허용되지 않는 메세지 타입", chatMessageType));
        }
    }

//    @Transactional
//    public void joinChatroom() {
//
//    }
//
//    @Transactional
//    public void leaveChatroom() {
//
//    }
//
//    @Transactional
//    public void getChatroomList() {
//
//    }
}
