
/*
var WebSocketClient = Meteor.npmRequire('websocket').client;
var client = new WebSocketClient();
 
client.on('connectFailed', function(error) {
    console.log('Connect Error: ' + error.toString());
});
 
client.on('connect', function(connection) {
    console.log('WebSocket Client Connected');
    connection.on('error', function(error) {
        console.log("Connection Error: " + error.toString());
    });
    connection.on('close', function() {
        console.log('echo-protocol Connection Closed');
    });
    connection.on('message', function(message) {
        if (message.type === 'utf8') {
            console.log("Received: '" + message.utf8Data + "'");
        }
    });
    
    function sendNumber() {
        if (connection.connected) {
            var number = Math.round(Math.random() * 0xFFFFFF);
            connection.sendUTF(number.toString());
            setTimeout(sendNumber, 1000);
        }
    }
    sendNumber();
});
 
client.connect('ws://localhost:8080/', 'echo-protocol');
*/


var websocket = null;
window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
    connect('ws://localhost:8080/');
}
connect = function(host) { // connect to the host websocket
    if ('WebSocket' in window)
        websocket = new WebSocket(host, 'echo-protocol');
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host, 'echo-protocol');
    else {
        console.log('error on connect');
        return;
    }
    websocket.onopen    = onOpen;
    websocket.onclose   = onClose;
    websocket.onmessage = onMessage;
    websocket.onerror   = onError;
    
}
function onOpen(event) {
    console.log('Connected to ' + window.location.host + '.');
    websocket.send("hello");
}

function onClose(event) {
    console.log('closed');
}

function onMessage(message) {
    console.log("Response: ");
    console.log(JSON.parse(message.data));
}

function onError(event) {
    console.log('error ' + event.data);
}

