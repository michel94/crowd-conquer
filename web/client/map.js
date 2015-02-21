function drawCell(cell){
    lat = cell.lat;
    lon = cell.lon;

    var coords = [
        new google.maps.LatLng(lat, lon),
        new google.maps.LatLng(lat, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon + 0.001),
        new google.maps.LatLng(lat + 0.001, lon),
        new google.maps.LatLng(lat, lon),
    ];

    pol = new google.maps.Polygon({
        paths: coords,
        strokeWeight: 0,
        fillColor: '#FF0000',
        fillOpacity: 0.35
    });

    pol.setMap(map);
}


Template.mapdiv.rendered = function(){
    var mapOptions = {
        zoom: 15,
        center: new google.maps.LatLng(40.186, -8.416),
        mapTypeId: google.maps.MapTypeId.TERRAIN
    };

    map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

    Cells.find().forEach(function(i, v){
        console.log("as");
        drawCell(v);
    });
    //drawCell(40.186, -8.416);
    //drawCell(40.187, -8.416);
    //drawCell(40.186, -8.417);


    //google.maps.event.addDomListener(window, 'load', initialize);
}
