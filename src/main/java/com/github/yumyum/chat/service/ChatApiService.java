package com.github.yumyum.chat.service;

import com.github.yumyum.chat.repository.FrendshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatApiService {

    private final FrendshipRepository frendshipRepository;


    @Transactional
    public void makeFreindship() {

    }

    @Transactional
    public void breakFreindship() {

    }

    @Transactional
    public void getFriendList() {

    }

    @Transactional
    public void createChatroom() {

    }

    @Transactional
    public void joinChatroom() {

    }

    @Transactional
    public void leaveChatroom() {

    }

    @Transactional
    public void getChatroomList() {

    }
}
