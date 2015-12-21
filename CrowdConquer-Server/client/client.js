


Mapbox.load({
	gl: true
})

// Using a template's rendered callback
Meteor.startup(function(){
    Mapbox.load();
});

Template.Home.rendered = function () {
	console.log('rendered')
    this.autorun(function () {
        if (Mapbox.loaded() && typeof L != 'undefined') {
            L.mapbox.accessToken = 'pk.eyJ1IjoibWljaGVsOTQiLCJhIjoiY2lmOG5tY2VwMDA0cHU4a29teHdxYXQ0eiJ9.ozJryFcasFMZBl9ODlKK4A';
            var map = L.mapbox.map('map', 'michel94.cif6pjief006sthm0b3gjm726');
        }
    });
};



// just for testing
websocket = null;
window.onload = function() {
	connect('wss://localhost:8080/');
}

function onOpen(){
	websocket.send(JSON.stringify({
		protocol: 'rpc',
		rpc: {
			methodName: 'hello',
			args: {}
		}
	}));
}
function onClose(){
}
function onMessage(message){
	console.log('Received ' + message.data)
}
function onError(){
}

connect = function(host) { // connect to the host websocket
	if ('WebSocket' in window)
		websocket = new WebSocket(host);
	else if ('MozWebSocket' in window)
		websocket = new MozWebSocket(host);
	else {
		console.log('error on connect');
		return;
	}
	websocket.onopen    = onOpen;
	websocket.onclose   = onClose;
	websocket.onmessage = onMessage;
	websocket.onerror   = onError;
	
}
