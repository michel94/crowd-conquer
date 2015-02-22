
Database = {
	getCell: function(x, y){
		var lon = Math.floor(x * 1000) / 1000;
		var lat = Math.ceil(y * 1000) / 1000;

		var cell = Cells.findOne({lon: lon, lat:lat});
		if(!cell){
			Cells.insert({lon: lon, lat:lat, value: 10, owner: 0});
			cell = Cells.findOne({lon: lon, lat:lat});
		}
		return cell;
	},
	getUser: function(email){
		var user = Users.findOne({email: email});
		if(!user){
			Users.insert({email: email, team: maxTeamId('team')+1} );
			var user = Users.findOne({email: email});
			Teams.insert({teamID: user.team, name: 'Solo'})
		}
		return Users.findOne({email: email});
	}

};

function maxTeamId(){
	var r = Users.find({}, {sort: {team: -1}}).fetch();
	if(r.length > 0 || !r){
		return r[0]['team'];
	}
	else
		return 0;
}
