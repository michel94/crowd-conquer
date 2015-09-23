
Meteor.startup(function(){
	wsServer = new WSServer(8080);

	function onRequest(request){
		// TODO verify request.origin

		var connection = request.accept(null, request.origin);
		console.log(new Date() + 'New connection');

		connection.on('message', function(message) {
			if (message.type === 'utf8') {
				console.log('Received Message: ' + message.utf8Data);
				connection.send(message.utf8Data)
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

	wsServer.open(onRequest, onError)
	
});


