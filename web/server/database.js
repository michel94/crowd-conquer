Database = {
    new_cell: function(x, y){
        lon = Math.floor(x * 1000) / 1000;
        lat = Math.ceil(y * 1000) / 1000;

        if(!Cells.findOne({lon: lon, lat:lat}))
            Cells.insert({lon: lon, lat:lat})
    }
};
