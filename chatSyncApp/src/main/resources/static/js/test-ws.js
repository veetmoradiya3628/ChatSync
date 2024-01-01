var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();
});

function connect(){
    const socket = new SockJS('/ChatSync/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/all/messages', function (response) {
            console.log(response);
            showGreeting(JSON.parse(response.body).content);
        });

        stompClient.subscribe('/user/specific', function (response) {
            showGreeting(JSON.parse(response.body).text);
        })
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage(){
    stompClient.send("/app/sendmessage", {}, JSON.stringify({'name': $("#message").val()}));
}


function sendPrivateMessage() {
    const text = document.getElementById('privateText').value;
    const to = document.getElementById('to').value;
    stompClient.send("/app/private", {}, JSON.stringify({'text':text, 'to':to}));
}