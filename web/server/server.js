Meteor.startup(function(){
	var user = User(Database.getUser("a@m"));
var user = User(Database.getUser("b@m"));
var user = User(Database.getUser("a@m"));
var user = User(Database.getUser("e@m"));

Pontuation.cell(35.62, 46.34).userKeepAlive('a@m');
console.log(Pontuation.cell(35.62, 46.34).users);
console.log(Pontuation.cell(35.62, 46.339999999).users);
console.log(Pontuation.cell(35.62, 46.3399998).users);
console.log(Cells.find().fetch());

})

