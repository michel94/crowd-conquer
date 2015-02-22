var map;
Session.set("initialized", false);
var colors = {};
var cellTeam;
var cellTeamID;

function drawCell(cell){
    var lat = cell.lat;
    var lon = cell.lon;

    var coords = [
        new google.maps.LatLng(lat, lon),
        new google.maps.LatLng(lat, lon + 0.001),
        new google.maps.LatLng(lat - 0.001, lon + 0.001),
        new google.maps.LatLng(lat - 0.001, lon),
        new google.maps.LatLng(lat, lon),
    ];

    var color = colors[cell.owner];
    if(color == null)
        color = colors[cell.owner] = '#'+(0x1000000+(Math.random())*0xffffff).toString(16).substr(1,6);

    var pol = new google.maps.Polygon({
        paths: coords,
        fillColor: color,
        strokeWeight: 1,
        strokeColor:'#666666',
        fillOpacity: 0.4
    });

    google.maps.event.addListener(pol,"mouseover", function(){
        this.setOptions({fillColor: "#08c"});
    });

    google.maps.event.addListener(pol,"mouseout", function(){
        this.setOptions({fillColor: color});
    });

    google.maps.event.addListener(pol, 'click', showArrays);
    infoWindow = new google.maps.InfoWindow();

    pol.setMap(map);
}

Template.map.rendered = function(){
    Session.set("initialized", true);
    cellTeamID = 4;
}

Template.map.helpers({
    dummy: function(){
        if(Session.get("initialized")){
            var mapOptions = {
                zoom: 15,
                center: new google.maps.LatLng(40.186, -8.416),
                mapTypeId: google.maps.MapTypeId.ROAD,
                backgroundColor: "#141414"
            };

            map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions)

            var styles = [{
                "stylers": [
                      { "invert_lightness": true },
                      { "hue": "#00a1ff" },
                      { "saturation": 30 }
                    ]
                }
                ,{
                    "featureType": "road",
                    "elementType": "labels",
                    "stylers": [{ "visibility": "off" }]
                }
                ,{
                    "featureType": "road.highway",
                    "stylers": [
                      { },
                      { "color": "#090909"},
                      { "saturation": 0 }
                    ]
                  }
                 ,{
                    "featureType": "water",
                    "stylers": [
                      { "color": "#0099FF" } ,
                      { "saturation": -60 }
                    ]
                  }
                 ,{
                    "featureType": "landscape",
                    "stylers": [
                      { "saturation": -90 },
                      { "hue": "#0099FF" }
                    ]
                  }];

            map.setOptions({styles: styles});

            var cells = Cells.find();
            cells.forEach(function(cell){
                if(cell.owner > 0)
                    drawCell(cell);
            });
        }
    },

    myTeam: function() {
        if (!Meteor.user()) return "";
        var id = Users.findOne({email:Meteor.user().services.google.email}).team;
        if (id)
            return Teams.findOne({teamID:id}).name;
        return "";
    },

    iHaveTeam: function() {
        if (!Meteor.user()) return false;
        var id = Users.findOne({email:Meteor.user().services.google.email}).team;
        console.log(id);
        if (id) return true;
        return false;
    },

    profilePicture : function() {
        return Meteor.user().services.google.picture;
    }
});

Template.map.events = {
    'click #createButton': function(){
        var name = $("#name").val();
        Meteor.call('createTeam', {'name': name});
        $(".main-panel").hide();
        $("#name").val("");
    }
}

/*Template.welcome.rendered = function(){
    Session.set("initialized", true);
}

Template.welcome.helpers({
    dummy: function(){
        if(Session.get("initialized")){
        var mapOptions = {
            zoom: 15,
            center: new google.maps.LatLng(40.186, -8.416),
            mapTypeId: google.maps.MapTypeId.ROAD,
            backgroundColor: "#141414"
        };

        map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

        var styles = [{
            "stylers": [
                  { "invert_lightness": true },
                  { "hue": "#00a1ff" },
                  { "saturation": 30 }
                ]
            }
            ,{
                "featureType": "road",
                "elementType": "labels",
                "stylers": [{ "visibility": "off" }]
            }
            ,{
                "featureType": "road.highway",
                "stylers": [
                  { },
                  { "color": "#090909"},
                  { "saturation": 0 }
                ]
              }
             ,{
                "featureType": "water",
                "stylers": [
                  { "color": "#0099FF" } ,
                  { "saturation": -60 }
                ]
              }
             ,{
                "featureType": "landscape",
                "stylers": [
                  { "saturation": -90 },
                  { "hue": "#0099FF" }
                ]
              }];

        map.setOptions({styles: styles});

        var cells = Cells.find();
        cells.forEach(function(cell){
            drawCell(cell);
        });
        }
    }
});*/

function showArrays(event) {
  var team = Cells.findOne({lat: event.latLng.lat().toFixed(6), lon: event.latLng.lng().toFixed(6) }).owner;
  var email = Users.findOne({team: team}).email;

  var vertices = this.getPath();

  var contentString = '<b>' + event.latLng.lat().toFixed(4) + ', ' + event.latLng.lng().toFixed(4) + '</b><br>' +
                      '<b>Controlled by: </b>' + email;

  infoWindow.setContent(contentString);
  infoWindow.setPosition(event.latLng);

  infoWindow.open(map);
}
