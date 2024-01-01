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
        stompClient.subscribe('/topic/messages', function (response) {
            console.log(response);
            showGreeting(JSON.parse(response.body).content);
        });
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage(){
    stompClient.send("/app/sendmessage", {}, JSON.stringify({'name': $("#message").val()}));
}