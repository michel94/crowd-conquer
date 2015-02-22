Meteor.startup(function(){
    tests();
});

Meteor.methods({
    /*login: function (email){
        Database.getUser(email);
    },*/
    createTeam: function (post) {
		console.log("create Team");
		var email = Meteor.user().services.google.email;
		var team = Users.findOne({email: email}).team;
		Teams.insert({'teamID': team, 'name': post.name});
    },
    joinTeam: function (post) {
    	var email = Meteor.user().services.google.email;

      	console.log("join Team");
    },
    leaveTeam: function (post) {
		console.log("leave Team");
    }
})
