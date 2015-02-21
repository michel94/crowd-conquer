Tracker.autorun(function() {
    if (Meteor.user()){
        email = Meteor.user().services.google.email;
        //console.log(email);
        if(!Users.findOne({email: email}))
            Users.insert({email: email});
    }
});

Template.das.helpers({
    d: function(){
        console.log(Users.find());
        return Users.find();
    }
});
