
Pontuation = {
	cells: {}
}

Pontuation.cell = function(x, y){
	if(!cells[x])
		cells[x] = {};
	if(!cells[x][y]){
		cells[x][y] = Cell(Database.getCell(x, y));
	}
}



Cell = function(data){
	var cell = {};
	cell.data = data;
	cell.users = {};
	cell.keepAlive = function(email){
		cell.users[email] = {time: 30}
	}

}


