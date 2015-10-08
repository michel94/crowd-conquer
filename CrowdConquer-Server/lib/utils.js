
/*
Object.prototype.contains = function(l){
	for(i=0; i<l.length; i++)
		if(!this.hasOwnProperty(l[i]))
			return false;
	return true;
} // TODO Causing Meteor error, perfectly working code
*/

truncate = function(num){
	if(num > 0)
		return Math.floor(num);
	else
		return Math.ceil(num);
}