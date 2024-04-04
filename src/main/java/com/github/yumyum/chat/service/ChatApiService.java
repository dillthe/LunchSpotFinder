package com.github.yumyum.chat.service;

import com.github.yumyum.chat.dto.*;
import com.github.yumyum.chat.entity.*;
import com.github.yumyum.chat.repository.*;
import com.github.yumyum.exceptions.InvalidFileException;
import com.github.yumyum.exceptions.InvalidMsgException;
import com.github.yumyum.exceptions.InvalidParamException;
import com.github.yumyum.exceptions.InvalidValueException;
import com.github.yumyum.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatApiService {

    private final MemberFriendRepository memberFriendRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChatroomQuerydslRepository chatroomQuerydslRepository;

    @Transactional
    public int isUsersFriend(final int memberId1, final int memberId2) {
        return friendshipRepository.existsFriendship(memberId1, memberId2);
    }

    @Transactional
    public List<Friendship> getFriends() {
        return friendshipRepository.findAll();
    }

    @Transactional
    public List<MemberDto> getMemberAllFriends(int id) {
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
                .member1(Member.builder().id(memberId1).build())
                .member2(Member.builder().id(memberId2).build())
                .build();
        Friendship friendship2 = Friendship.builder()
                .member1(Member.builder().id(memberId2).build())
                .member2(Member.builder().id(memberId1).build())
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
            throw new InvalidValueException(String.format("%s와 %s는 이미 친구가 아닙니다.", id1, id2));
        } else {
            List<Friendship> friendships1 = friendshipRepository.findByMemberId1AndMemberId2(id1, id2);
            List<Friendship> friendships2 = friendshipRepository.findByMemberId1AndMemberId2(id2, id1);

            friendshipRepository.delete(friendships1.get(0));
            friendshipRepository.delete(friendships2.get(0));
            log.info(String.format("%s와 %s 친구 관계 제거 완료", id1, id2));
        }
    }

    public String checkMembersFriendShip(int userId1, int friendShipSearchId) throws InvalidValueException{
        if (userId1 == friendShipSearchId) {
            throw new InvalidValueException("자기 자신과 친구 관계를 맺을 수 없습니다.");
        }

        if (isUsersFriend(userId1, friendShipSearchId) > 0) {
            throw new InvalidValueException(String.format("%s와 %s는 이미 친구입니다.", userId1, friendShipSearchId));
        }

        makeFriend(userId1, friendShipSearchId);
        return String.format("%s와 %s는 친구가 되었습니다.", userId1, friendShipSearchId);
    }

    public List<MemberDto> searchMembers(String keyword) {
        return memberQuerydslRepository.findByUsernameContainingOrEmailContaining(keyword);
    }

    public List<MemberDto> getAllMembers() {
        List<Member> members = memberFriendRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            memberDtos.add(MemberDto.builder()
                    .id(member.getId())
                    .loginId(member.getLoginId())
                    .memberName(member.getMemberName())
                    .build());
        }
        return memberDtos;
    }

    @Transactional
    public void createChatroom(ChatroomDto chatroomDto) throws IOException {
        chatroomQuerydslRepository.saveChatroom(chatroomDto);
    }

    public List<MemberDto> getChatroomMembers(Integer chatroomId) {
        return chatroomQuerydslRepository.getChatroomMembers(chatroomId);
    }

    public long leaveChatroomMember(LeaveChatDto leaveChatDto) {
        return chatroomQuerydslRepository.deleteMemberChatroom(leaveChatDto);
    }

    public void updateChatroom(Integer chatroomId, ChatroomUpdateDto chatroomUpdateDto) throws IOException {
        chatroomQuerydslRepository.updateChatroom(chatroomId, chatroomUpdateDto);
    }

    public void saveChatContent(ChatMessage chatMessage) throws IOException {

        MessageType chatMessageType = chatMessage.getType();
        Integer memberId = chatMessage.getMemberId();
        Integer roomId = chatMessage.getRoomId();

        MemberChatroom memberChatroom = getMemberChatroom(memberId, roomId);

        if (chatMessageType == MessageType.CHAT_TEXT) {
            String content = chatMessage.getContent();

            ChatContent chatContent = ChatContent
                    .builder()
                    .text(content)
                    .memberChatroom(memberChatroom)
                    .build();
            chatroomQuerydslRepository.save(chatContent);
        } else if (chatMessageType == MessageType.CHAT_IMG) {
            MultipartFile messageFile = chatMessage.getFile();
            if (messageFile == null) {
                throw new InvalidFileException("해당 파일은 빈 파일 입니다.");
            }
            ChatContent chatContent = ChatContent
                    .builder()
                    .memberChatroom(memberChatroom)
                    .build();
            chatContent.setImg(messageFile.getBytes());
            chatroomQuerydslRepository.save(chatContent);
        } else if (chatMessageType == MessageType.CHAT_GAME) {
            log.info("game 결과 저장");
        } else {
            throw new InvalidMsgException(String.format("%s는 허용되지 않는 메세지 타입", chatMessageType));
        }
    }

    public void saveChatContent(ChatMessageDto chatMessageDto, Integer chatroomId) throws IOException {

        MessageType chatMessageType = chatMessageDto.getType();
        Integer memberId = chatMessageDto.getMemberId();

        MemberChatroom memberChatroom = getMemberChatroom(memberId, chatroomId);
        log.info("memberChatroom1: {}", memberChatroom);

        if (chatMessageType == MessageType.CHAT_TEXT) {
            String content = chatMessageDto.getContent();

            ChatContent chatContent = ChatContent
                    .builder()
                    .text(content)
                    .memberChatroom(memberChatroom)
                    .build();
            chatroomQuerydslRepository.save(chatContent);

        } else if (chatMessageType == MessageType.CHAT_IMG) {
            MultipartFile messageFile = chatMessageDto.getFile();
            if (messageFile == null) {
                throw new InvalidFileException("해당 파일은 빈 파일 입니다.");
            }
            log.info("memberChatroom2: {}", memberChatroom);
            ChatContent chatContent = ChatContent
                    .builder()
                    .memberChatroom(memberChatroom)
                    .build();
            chatContent.setImg(messageFile.getBytes());
            log.info("chatContent: {}", chatContent);
            chatroomQuerydslRepository.save(chatContent);

        } else if (chatMessageType == MessageType.CHAT_GAME) {
            log.info("game 결과 저장");

        } else {
            throw new InvalidMsgException(String.format("%s는 허용되지 않는 메세지 타입", chatMessageType));
        }
    }

    private MemberChatroom getMemberChatroom(Integer memberId, Integer roomId) {
        MemberChatroom memberChatroom;
        if (memberId != null && roomId != null) {
            memberChatroom = MemberChatroom
                    .builder()
                    .member(
                            Member
                                .builder()
                                .id(memberId)
                                .build()
                    )
                    .chatroom(
                            Chatroom
                                .builder()
                                .chatroomId(roomId)
                                .build()
                    )
                    .build();
        } else {
            throw new InvalidParamException(String.format("파라미터에 null 값이 올 수 없습니다.(memberId: %s, roomId: %s )", memberId, roomId));
        }
        return memberChatroom;
    }

    public void saveChatTextContent(ChatTextMessage chatTextMessage) {
        String content = chatTextMessage.getContent();
        MemberChatroom memberChatroom = getMemberChatroom(chatTextMessage.getMemberId(), chatTextMessage.getRoomId());

        ChatContent chatContent = ChatContent.builder()
                .text(content)
                .memberChatroom(memberChatroom)
                .build();

        chatroomQuerydslRepository.save(chatContent);
    }

    public void saveChatImgContent(ChatImgMessage chatImgMessage) throws IOException {
        MultipartFile imgFile = chatImgMessage.getFile();
        MemberChatroom memberChatroom = getMemberChatroom(chatImgMessage.getMemberId(), chatImgMessage.getRoomId());

        ChatContent chatContent = ChatContent.builder()
                .memberChatroom(memberChatroom)
                .build();
        chatContent.setImg(imgFile.getBytes());

        chatroomQuerydslRepository.save(chatContent);
    }

}
