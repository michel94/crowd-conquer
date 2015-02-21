
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

Pontuation.updateCells = function(){

}

Cell = function(data){
	var cell = {};
	cell.data = data;
	cell.users = {};
	cell.userKeepAlive = function(email){
		cell.users[email] = {time: 30}
	}

}


