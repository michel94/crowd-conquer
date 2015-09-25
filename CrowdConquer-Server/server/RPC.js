
Meteor.superMethods = Meteor.methods;
RPC = {enabled: true}

Meteor.methods = function(methods){
	RPC.methods = methods
	Meteor.superMethods(methods)

}

function contains(d, l){
	for(i=0; i<l.length; i++)
		if(!d.hasOwnProperty(l[i]))
			return false;
	return true;
}

RPC.parseData = function(data){ 
	/* receives string data to parse, 
	returns method name and arguments */

	try{
		data = JSON.parse(data);
		if(data.protocol != 'rpc' || !data.hasOwnProperty('rpc') )
			return -1; //TODO Use a proper error code/ throw error. Probably some research is needed here.
	}catch(e){
		console.log(e)
		return -1;
	}

	rpcData = data.rpc;
	
	if(!contains(rpcData, ['methodName', 'args']))
		return -2; //TODO

	methodName = rpcData.methodName;
	args = RPC.argsToList(methodName, rpcData.args);

	return {methodName: methodName, args: args};

}

RPC.argsToList = function(method_name, args){ 
	/* Receives args Object and returns arguments in 
	a sorted list to be passed directly to the method */

	var argList = RPC.methods[method_name].toString()
		.replace(/((\/\/.*$)|(\/\*[\s\S]*?\*\/)|(\s))/mg,'')
		.match(/^function\s*[^\(]*\(\s*([^\)]*)\)/m)[1]
		.split(/,/);
	if(argList.length == 1 && argList[0] == '')
		argList = []
	var sortedArgs = new Array(argList.length);
	for(i=0; i<argList.length; i++){
		if(args.hasOwnProperty(argList[i]) )
			sortedArgs[i] = args[argList[i]];
		else
			return -1;
	}
	return sortedArgs;
}
