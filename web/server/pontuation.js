
Pontuation = {
	cells: {}
}

Pontuation.cell = function(lon, lat){
	if(!cells[lon])
		cells[lon] = {};
	if(!cells[lon][lat]){
		cells[lon][lat] = Cell(Database.getCell(lon, lat));
	}
}

//Meteor.setInterval(Pontuation.updateCells, 20000);

Pontuation.updateCells = function(){
	for(i=0; i<this.cells.length; ){
		var user = this.cells[i];
		user.time--;
		if(user.time == 0){
			this.cells[i].teams[user.team] = ;
			this.cells.splice(i, 1);
		}else{
			i++;
		}
	}
}

Cell = function(data){
	var cell = {};
	cell.data = data;
	cell.time = 0;
	cell.users = {};
	cell.teams = {};
	cell.userKeepAlive = function(email){
		cell.users[email] = {time: 30}
	}

}

User = function(email, team){
	var user = {};
	user.email = email;
	user.team = team;
}
