
websocket = null;
window.onload = function() { // URI = ws://10.16.0.165:8080/WebSocket/ws
    connect('wss://localhost:8080/');
}

connect = function(host) { // connect to the host websocket
    if ('WebSocket' in window)
        websocket = new WebSocket(host);
    else if ('MozWebSocket' in window)
        websocket = new MozWebSocket(host);
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
    console.log('Connected WebSocket');

    Connection.call('ping', {x: 13.42, y: 37.46, user: 'nabo'});
}

send = function(message){
    console.log('sending')
    if(websocket != null)
        websocket.send(message);
}

function onClose(event) {
    E = event;
    console.log('closed');
}

function onMessage(message) { 
    console.log("Response: ");
    //console.log(JSON.parse(message.data));
    $("#log").append('<p>' + message.data + '</p>');
}

function onError(event) {
    E = event;
    console.log('error ' + event.data);
}
 
