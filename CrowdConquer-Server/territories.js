
EARTH_RADIUS = 6400; // km
CELL_SIDE = 1.0; // km
LAT_ANGLE_DIV = Math.asin(CELL_SIDE/EARTH_RADIUS); // CELL_SIZE = EARTH_RADIUS * sin(ANG) // radians

function calcLatId(lat){
	lat_id = truncate(lat/LAT_ANGLE_DIV);
	return lat_id;
}

function calcLatPerimeter(lat_id){
	lat = lat_id * LAT_ANGLE_DIV
	r = EARTH_RADIUS * Math.cos(lat) // Radius in km
	P = 2 * r * Math.PI;
	return P;
}

function calcLonId(lat_id, lon){
	P = calcLatPerimeter(lat_id);
	n_cells = P / CELL_SIDE;
	console.log("n_cells", n_cells)
	/*	180 ------- n_cells
		lon ------- x*/
	lon_id = truncate(lon * n_cells / Math.PI);
	return lon_id;

}

function mapCoords(lat, lon){
	y = calcLatId(lat);
	x = calcLonId(y, lon);
	return {x: x, y: y}
}

fetchTerritory = function(){
	
}

console.log(LAT_ANGLE_DIV);

coords = mapCoords(-Math.PI / 2, Math.PI * 3 / 4);
console.log(coords);
