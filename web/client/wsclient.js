/*
	Websocket client for testing only: the client must be the android app.
*/

websocket = null;
var curCallbackId = 1;
var callbacks = {}
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
    rpcCall('ping', {x: 13.42, y: 37.46, user: 'nabo'}, function (e, r){console.log(r)}); // test
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
	var message = JSON.parse(message.data);
	if(message.method == 'rpc_callback'){
		var ret = message.rpc_callback;
		if(typeof callbacks[ret.callback_id] == 'function')
			callbacks[ret.callback_id](ret.error, ret.data);
	}
}

function onError(event) {
	console.log('error ' + event.data);
}

function getNextCallbackId(){
	return curCallbackId++;
}
 
rpcCall = function(name, args, callback){
	if(websocket != null){
		var message = {
			method: "rpc",
			rpc: {
				method_name: name,
				args: args
			}
		}
		if(typeof callback == 'function'){
			var call_id = getNextCallbackId();
			callbacks[call_id] = callback;
			message.rpc.callback_id = call_id;
		}
		var message = JSON.stringify(message);
		websocket.send(message);
	}
}
