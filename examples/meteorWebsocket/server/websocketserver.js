
var http = Meteor.npmRequire('http');
var WebSocketServer = Meteor.npmRequire('websocket').server;
Fiber = Meteor.npmRequire('fibers');

var server = http.createServer(function(request, response) {
	console.log((new Date()) + ' Received request for ' + request.url);
	response.writeHead(404);
	response.end();
});
server.listen(8080, function() {
	console.log((new Date()) + ' Server is listening on port 8080');
});

wsServer = new WebSocketServer({
	httpServer: server,
	// You should not use autoAcceptConnections for production applications
	// You should verify the connection's origin and decide whether or not to accept it. 
	autoAcceptConnections: false
});

function originIsAllowed(origin) {
	// put logic here to detect whether the specified origin is allowed.
	return true;
}

wsServer.on('request', function(request) {
	if (!originIsAllowed(request.origin)) {
		request.reject();
		console.log((new Date()) + ' Connection from origin ' + request.origin + ' rejected.');
		return;
	}
	
	var connection = request.accept('echo-protocol', request.origin);
	console.log((new Date()) + ' Connection accepted.');

	connection.on('message', function(message) {
		if (message.type === 'utf8') {
			console.log('Received Message: ' + message.utf8Data);

			message = JSON.parse(message.utf8Data);
			console.log(message);
			if(message.method == 'rpc'){
				runRpc(message.rpc);
			}
			
		}
		else if (message.type === 'binary') {
			console.log('Received Binary Message of ' + message.binaryData.length + ' bytes');
			connection.sendBytes(message.binaryData);
		}
	});
	connection.on('close', function(reasonCode, description) {
		console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
	});
});

Meteor.functions = {
	ping: function(x, y, user){
		console.log('Received ping from user ' + user + ' at ' + x + ', ' + y);
	}
}

Meteor.methods(Meteor.functions);
