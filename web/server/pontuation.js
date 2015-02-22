
Pontuation = {
	cells: {}
}

Pontuation.cell = function(lon, lat){
	var cell = Database.getCell(lon, lat);
	lon = cell.lon;
	lat = cell.lat;
	if(!this.cells[lon])
		this.cells[lon] = {};
	if(!this.cells[lon][lat]){
		this.cells[lon][lat] = Cell(cell);
	}
	return this.cells[lon][lat];
}


Pontuation.updateUsers = function(){
	var p = Pontuation;
	for(var row in p.cells){
		for(var col in p.cells[row]){
			var users = p.cells[row][col].users;
			for(var email in users){
					var user = users[email];
				if(user){
					user.time--;
					if(user.time <= 0){
						p.cells[row][col].teams[user.team]--;
						delete users[email];
					}
				}
			}
		}
	}
}
Meteor.setInterval(Pontuation.updateUsers, 1000);

Meteor.setInterval(function(){
	Pontuation.cell(-8.416, 40.185).userKeepAlive("a@m");
}, 10000);

Pontuation.updateCells = function(){
	var p = Pontuation;
	var C=0.1;

	for(var row in p.cells){
		for(var col in p.cells[row]){
			var cell = p.cells[row][col];
			if(cell.owner == 0){
				if(!cell.hasOwnProperty("avail"))
					cell.avail = cell.value;
				for(var t in cell.teams){
					console.log(t, cell.ownership[t]);
					console.log(cell.avail);
					cell.avail -= cell.teams[t] * C * cell.value;
					if(cell.avail < 0){
						cell.ownership[t] += cell.avail;
						cell.avail = 0;
						break;
					}
					if(!cell.ownership.hasOwnProperty(t) )
						cell.ownership[t] = 0;
					cell.ownership[t] += cell.teams[t] * C * cell.value;
				}
				if(cell.avail == 0){
					var m = 0, mi;
					for(var t in cell.ownership){
						if(cell.ownership[t] > m){
							m = cell.ownership[t];
							mi = t;
						}
					}
					cell.owner = mi;
					console.log('New owner is team ' + cell.owner);
				}
			}
			/*for(var t in cell.teams){
				if(t != owner)
			}*/
		}
	}
	
}

			
Meteor.setInterval(Pontuation.updateCells, 1000);

User = function(data){
	var user = {};
	//console.log(data);
	user.email = data.email;
	user.team = data.team;
	user.time = 30;
	return user;
}

Cell = function(data){
	var cell = {};
	cell.value = data.value;
	cell.lon = data.lon;
	cell.lat = data.lat;
	cell.users = {}; // current users: maps email to User
	cell.teams = {}; // number of elements of each team in this cell
	cell.ownership = {}; // empty ownership means that no one owns the cell
	cell.owner = 0;
	cell.userKeepAlive = function(email){
		var user = this.users[email];
		if(user){
			user.time = 30;
		}else{
			this.users[email] = User(Database.getUser(email));
			var user = this.users[email];
			if(!this.teams[user.team])
				this.teams[user.team] = 0
			this.teams[user.team]++;
			console.log(this.teams[user.team]);
		}
	}

	return cell;
}

//var cell = Pontuation.cell(35.62, 46.34);
//Users.remove({});



//console.log(Users.find({}).fetch());

//console.log(user);

//console.log(cell);

//console.log(Cells.find().fetch());

tests = function(){
	Users.remove({});
	var user = User(Database.getUser("a@m"));
	var user = User(Database.getUser("b@m"));
	var user = User(Database.getUser("a@m"));
	var user = User(Database.getUser("e@m"));

	Pontuation.cell(-8.416, 40.186);
	Pontuation.cell(-8.416, 40.187);
	Pontuation.cell(-8.416, 40.185);
	Pontuation.cell(-8.416, 40.184);
	Pontuation.cell(-8.416, 40.180);
	Pontuation.cell(-8.413, 40.186);
	Pontuation.cell(-8.411, 40.189);
	Pontuation.cell(-8.4183232, 40.1883123);

	Pontuation.cell(-8.416, 40.185).userKeepAlive("a@m");

	//console.log(Pontuation.cell(35.62, 46.34).users);
	//console.log(Pontuation.cell(35.62, 46.339999999).users);
	//console.log(Pontuation.cell(35.62, 46.3399998).users);

	//console.log(Cells.find().fetch());

}
