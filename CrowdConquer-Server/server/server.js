Fiber = Meteor.npmRequire('fibers');

Meteor.startup(function(){
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
						Meteor.apply(result.methodName, result.args)
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

	wsServer.open(onRequest, onError)
	

	Meteor.methods({
		hello: function(){
			console.log('ok');
		}
	});

	//d = {a:0, b:1, c:0};
	//console.log(d.contains(['a', 'b']));
	//console.log(d.contains(['a', 'e']));
});

