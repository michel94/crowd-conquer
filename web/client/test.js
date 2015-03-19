
Template.Chat.events = {
	'keypress #toSend': function(e, template){
		if(e.which === 13){
			var message = e.target.value;
			e.target.value = '';
			var t = template.find('#hist');
			rpcCall('sendMessage', {user: 'random', message: message}, function(e, r){
				if(r == 'ok'){
					t.value = message + '\n' + t.value;
					console.log('message sent successfully');
				}

			});
		}
	}
}