
Template.test.events = {
	'keypress input.message': function (evt, template) {
		if (evt.which === 13) {
			var message = $(evt.target).val();
			$(evt.target).val('');
			send(message);
		}
	}
}
