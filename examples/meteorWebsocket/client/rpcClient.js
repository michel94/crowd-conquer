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

