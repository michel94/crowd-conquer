


function addObjects(a, b){
	var c = {}
	for(var k in a){
		if(k in b){
			if(typeof a[k] == 'object' && typeof b[k] == 'object')
				c[k] = addObjects(a[k], b[k])
			else
				c[k] = a[k] + b[k];
		}else{
			c[k] = a[k];
		}
	}
	for(var k in b){
		if(!(k in a)){
			c[k] = b[k]
		}
	}
	return c;
}

function addLists(){

}



Module = {}
Module.addObjects = addObjects;
Module.addLists = addLists;
