


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


