Router.route('/', function () {
    if (Meteor.user() || Meteor.loggingIn()) {
        this.render('map', { });
    }
    else {
        this.render('welcome', { });
    }
});

Tracker.autorun(function() {
    if (Meteor.user()){
        email = Meteor.user().services.google.email;

        Meteor.call("login", email);
        //console.log(email);
        //if(!Users.findOne({email: email}))
        //    Users.insert({email: email});
    }
});
