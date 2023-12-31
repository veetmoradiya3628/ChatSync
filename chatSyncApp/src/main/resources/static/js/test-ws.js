const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ChatSync/ws'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/messages', function(response) {
        console.log(response);
        showGreeting(JSON.parse(response.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};


function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/app/sendmessage",
        body: JSON.stringify({'name': $("#message").val()})
    })
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
})
