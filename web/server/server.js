Meteor.startup(function(){
    //tests();
});

Meteor.methods({
    myLogin: function (email){
        Database.getUser(email);
    },
    createTeam: function (post) {
		var email = Meteor.user().services.google.email;
		var id = Users.findOne({email: email}).team;
		Teams.insert({'teamID': id, 'name': post.name});
        var id = Teams.find().fetch().length+1;
        Users.findOne({email:Meteor.user().services.google.email}).team = id;
        Users.update({email:Meteor.user().services.google.email}, {$set: {team:id}});
    },
    joinTeam: function (post) {
    	var email = Meteor.user().services.google.email;
      	console.log("join Team");
    },
    leaveTeam: function (post) {
        Users.update({email:Meteor.user().services.google.email}, {$set: {team:null}});
    }
});
