websocket = null;
window.onload = function() {
	connect('wss://localhost:8080/');
}

function onOpen(){
	websocket.send('hello');
}
function onClose(){
}
function onMessage(message){
	console.log('Received ' + message.data)
}
function onError(){
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
