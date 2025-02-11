'use strict';
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
console.log('a');

let stompClient = null;
let memberId = 0;
let roomId = 0;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// 연결 시도
function connect(event) {
    memberId = parseInt(document.querySelector('#memberId').value);
    roomId = parseInt(document.querySelector('#roomId').value);
    console.log("memberId: " + memberId);
    console.log("roomId: " + roomId);

    if(memberId) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function() {
            onConnected(roomId);
        }, onError);
    }
    event.preventDefault();
}

// 연결 성공시 실행할 콜백 함수
function onConnected(roomId) {

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/' + roomId + '/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat/" + roomId + "/addUser",
        {},
        JSON.stringify({
            memberId: memberId,
            roomId: roomId,
            type: 'JOIN'
        })
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        // chatMessage 객체 생성
        var chatMessage = {
            roomId: roomId,
            sender: memberId,
            content: messageInput.value,
            type: 'CHAT_TEXT'
        };
        stompClient.send("/app/chat/" + roomId + "/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var memberIdElement = document.createElement('span');
        var memberIdText = document.createTextNode(message.sender);
        memberIdElement.appendChild(memberIdText);
        messageElement.appendChild(memberIdElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)