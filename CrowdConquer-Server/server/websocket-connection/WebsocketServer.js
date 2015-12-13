
var https = Meteor.npmRequire('https');
var fs = Meteor.npmRequire('fs');
var WebSocketServer = Meteor.npmRequire('websocket').server;


WSServer = function(port){
	var appRoot = process.env.PWD;

	var options = {
		key: fs.readFileSync(appRoot + '/keys/server.key'),
		cert: fs.readFileSync(appRoot + '/keys/server.crt')
	};

	this.port = port;
	server = https.createServer(options, function(request, response) {
		response.writeHead(200);
		response.end();
	});

	this.open = function(onRequest, onError){
		server.listen(this.port, function() {
			console.log((new Date()) + ' Server is listening on port ' + port);
		});
		
		this.wsServer = new WebSocketServer({
			httpServer: server,
			autoAcceptConnections: false
		});

		this.wsServer.on('error', onError);

		this.wsServer.on('request', onRequest);
		
	}.bind(this);	

}


setupWebsocket = function(){
	wsServer = new WSServer(8080);

	function onRequest(request){
		// TODO verify request.origin

		var connection = request.accept(null, request.origin);
		console.log(new Date() + 'New connection');

		connection.on('message', function(message) {
			if (message.type === 'utf8') {
				console.log('Received Message: ' + message.utf8Data);
				//connection.send(message.utf8Data);

				if(RPC.enabled){
					result = RPC.parseData(message.utf8Data)
					console.log(result) //TODO IMPORTANT Check if result is error code or Object

					Fiber(function(){
						if(!result.hasOwnProperty('callbackId') )
							Meteor.apply(result.methodName, result.args);
						else{
							Meteor.apply(result.methodName, result.args, function(error, response){
								ret = JSON.stringify({
									protocol: 'rpcCallback',
									rpcCallback: {
										error: error,
										response: response,
										callbackId: result.callbackId
									}
								})
								console.log('send callback ' + ret);
								connection.send(ret)
							});
						}
					}).run();
				}

			}else if (message.type === 'binary') {
				console.log('Received Binary Message of ' + message.binaryData.length + ' bytes');
				connection.sendBytes(message.binaryData);
			}

		});

		connection.on('close', function(reasonCode, description) {
			console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
		});
	}

	function onError(e){
		console.log('Error', e);
	}

	wsServer.open(onRequest, onError);

}

