/*
	Websocket client for testing only: the client must be the android app.
*/

websocket = null;
window.onload = function() {
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
    rpcCall('ping', {x: 13.42, y: 37.46, user: 'nabo'});
}

send = function(message){
    if(websocket != null)
        websocket.send(message);
}

function onClose(event) {
	// Reconnect code here
    console.log('closed');
}

function onMessage(message) {
    console.log("Response: ");
}

function onError(event) {
    console.log('error ' + event.data);
}
 
rpcCall = function(name, args){
	if(websocket != null){
		var message = JSON.stringify({
			method: "rpc",
			rpc: {
				method_name: name,
				args: args
			}
		});
		websocket.send(message);
	}
}