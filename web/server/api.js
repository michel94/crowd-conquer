Router.onBeforeAction(Iron.Router.bodyParser.urlencoded({
        extended: false
    }));

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
			json = eval(json);
		}catch(err){
            console.log(err);
			response(this, 'Json could not be parsed');
			return;
		}

    console.log(json.lon, json.lat, json.email);
    Database.getUser(json.email);

    var cell = Pontuation.cell(json.lon, json.lat)
    cell.userKeepAlive(json.email);

		this.response.writeHead(200, {'Content-Type': 'application/json'});
    console.log("ratio: " + (cell.value-cell.avail)/cell.value);
    this.response.end(JSON.stringify({
            owner: parseInt(cell.owner),
            team: Users.findOne({email: json.email}).team,
            ratio: (cell.value-cell.avail)/cell.value
        }));
	});

Router.route('/api/:others', {where: 'server'})
  .post(function(){
    response(this, 'No api function here');
  })
