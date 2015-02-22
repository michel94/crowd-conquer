var map;
Session.set("initialized", false);

function drawCell(cell){
    var lat = cell.lat;
    var lon = cell.lon;

    console.log("a");

    var coords = [
        new google.maps.LatLng(lat, lon),
        new google.maps.LatLng(lat, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon),
        new google.maps.LatLng(lat, lon),
    ];

    var pol = new google.maps.Polygon({
        paths: coords,
        fillColor: '#FF0000', //User.findOne({team: 1}).color,
        strokeWeight: 1,
        strokeColor:'#666666',
        fillOpacity: 0.4
    });

    pol.setMap(map);
}

Template.map.rendered = function(){
    Session.set("initialized", true);
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
                if(cell.owner > 0)
                    drawCell(cell);
            });
        }
    }
});

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
