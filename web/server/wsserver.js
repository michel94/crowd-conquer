

var https = Meteor.npmRequire('https');
var WebSocketServer = Meteor.npmRequire('websocket').server;
var fs = Meteor.npmRequire('fs');
var path = process.env.PWD;
Fiber = Meteor.npmRequire('fibers');

var serverOptions = {
	key: fs.readFileSync(path + '/server/ssl/key.pem'),
	cert: fs.readFileSync(path + '/server/ssl/cert.pem'),
	passphrase: 'asdfzxcv'
};

var server = https.createServer(serverOptions, function(request, response) {
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
	// connection's origin must be verified to decide whether or not to accept it. 
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
		console.log('received message');
		if (message.type === 'utf8') {
			console.log(message.utf8Data)

			message = JSON.parse(message.utf8Data);
			if(message.method == 'rpc'){
				runRpc(connection, message.rpc);
			}else{
				connection.sendUTF('Unrecognized method');
			}
		}
	});

	connection.on('close', function(reasonCode, description) {
		console.log((new Date()) + ' Peer ' + connection.remoteAddress + ' disconnected.');
	});
});

function parseRpc(method_name, args){
	var argList = meteorCalls[method_name].toString()
		.replace(/((\/\/.*$)|(\/\*[\s\S]*?\*\/)|(\s))/mg,'')
		.match(/^function\s*[^\(]*\(\s*([^\)]*)\)/m)[1]
		.split(/,/); // gets arguments in function header, applying regex after toString() 
	var sortedArgs = new Array(argList.length);
	for(i=0; i<argList.length; i++){
		sortedArgs[i] = args[argList[i]]; //obtain the corresponding value on the object argument
	}
	return sortedArgs;
}

runRpc = function(connection, object){
	/* 	Run remote method
		If the function returned something and a callback_id has been specified,
		deliver response with method = "rpc_callback"
		Needs fiber because calls meteor code inside node.js code */
	
	var args = parseRpc(object.method_name, object.args);
	var callback_id = object.callback_id;
	Fiber(function(){
		var r = Meteor.apply(object.method_name, args);
		if(typeof r !== 'undefined'){
			if(typeof object.callback_id !== 'undefined'){ 
				var ret = {
					method: "rpc_callback",
					rpc_callback: {
						callback_id: object.callback_id,
						data: r,
						error: null
					}
				}
				connection.sendUTF(JSON.stringify(ret));
			}
		}
	}).run();
}


