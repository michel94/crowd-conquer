var map;
Session.set("initialized", false);

function drawCell(cell){
    var lat = cell.lat;
    var lon = cell.lon;

    var coords = [
        new google.maps.LatLng(lat, lon),
        new google.maps.LatLng(lat, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon),
        new google.maps.LatLng(lat, lon),
    ];

    var pol = new google.maps.Polygon({
        paths: coords,
        strokeWeight: 0,
        fillColor: '#FF0000',
        fillOpacity: 0.35
    });

    console.log("worka");

    pol.setMap(map);
}

Template.mapdiv.rendered = function(){
    Session.set("initialized", true);
}

Template.mapdiv.helpers({
    dummy: function(){
        if(Session.get("initialized")){
            var mapOptions = {
                zoom: 15,
                center: new google.maps.LatLng(40.186, -8.416),
                mapTypeId: google.maps.MapTypeId.TERRAIN
            };

            map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

            console.log(Cells.find().fetch());


            var cells = Cells.find();
            cells.forEach(function(cell){
                drawCell(cell);
            });
            //drawCell({lat: 40.186, lon: -8.416}, map);
            //drawCell(40.187, -8.416, map);
            //drawCell(40.186, -8.417);
        }
    }

});
