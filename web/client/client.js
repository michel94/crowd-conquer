Router.route('/', function () {
    if (Meteor.user() || Meteor.loggingIn()) {
        Session.set("initialized", false);
        this.render('map', { });
    }
    else {
        Session.set("initialized", false);
        this.render('welcome', { });
    }
});

Tracker.autorun(function() {
    if (Meteor.user()){
        email = Meteor.user().services.google.email;
        //console.log(email);
        if(!Users.findOne({email: email}))
            Users.insert({email: email});
    }
});
