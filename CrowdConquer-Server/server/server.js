Fiber = Meteor.npmRequire('fibers');

Meteor.startup(function(){
	setupWebsocket();
	
	Meteor.methods({
		hello: function(){
			console.log('hello function');

			return {info: 'Everything looks nice'};
		}
	});

});

