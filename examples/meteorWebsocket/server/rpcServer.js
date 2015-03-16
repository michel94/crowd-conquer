function parseRpc(method_name, args){
	var argList = Meteor.functions[method_name].toString()
		.replace(/((\/\/.*$)|(\/\*[\s\S]*?\*\/)|(\s))/mg,'')
		.match(/^function\s*[^\(]*\(\s*([^\)]*)\)/m)[1]
		.split(/,/);
	var sortedArgs = new Array(argList.length);
	for(i=0; i<argList.length; i++){
		sortedArgs[i] = args[argList[i]];
	}
	return sortedArgs;
}

runRpc = function(object){
	var args = parseRpc(object.method_name, object.args);
	console.log(args);
	
	Fiber(function(){
		return Meteor.apply(object.method_name, args);
	}).run();
}