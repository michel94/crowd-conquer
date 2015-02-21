Router.onBeforeAction(Iron.Router.bodyParser.urlencoded({
        extended: false
    }));

/*Router.route('/api/example/', {where: 'server'})
  .get(function () {
  	console.log(this.params);
    this.response.end('get request\n');
  })
  .post(function () {
  	console.log(this.params);
  	console.log(this.request);
  	console.log(this.body);
  	console.log(this.data);

  	var json = this.request.body
  	console.log(json);

  	this.response.writeHead(200, {'Content-Type': 'application/json'});
    this.response.end(JSON.stringify({asd: 'asd'}) );
  });*/

function response(post, message){
	post.response.writeHead(400, {'Content-Type': 'application/json'});
    post.response.end(JSON.stringify({'message': message}) );
}

Router.route('/api/location', {where: 'server'})
	.post(function(){
		if(!this.request.body.args){
			response(this, 'No args parameter found');
			return;
		}

		var json = this.request.body.args;
		try{
			json = JSON.parse(json);
		}catch(err){
			response(this, 'Json could not be parsed');
			return;
		}

		this.response.writeHead(200, {'Content-Type': 'application/json'});
    	this.response.end(JSON.stringify({'time': 60}));

	});
