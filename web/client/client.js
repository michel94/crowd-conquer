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

Database = {
    new_cell: function(x, y){
        new_x = Math.ceil(x * 1000) / 1000;
        new_y = Math.floor(y * 1000) / 1000;

        console.log(new_x, new_y);

    }
};
