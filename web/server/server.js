
meteorCalls = {
	ping: function(x, y, user){
		console.log('Received ping from user ' + user + ' at ' + x + ', ' + y);
	},
	sendMessage: function(message, user){
		console.log('Received ' + message + ' from user ' + user);
	}
}

Meteor.methods(meteorCalls); // a not very pretty hack

