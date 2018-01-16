var bounds = [
    [47.3, 5.9], // Southwest coordinates
    [54.9, 16.9512215]  // Northeast coordinates
];
var map = new L.map('map').setView([50.5 , 9.125], 6).setMaxBounds(bounds);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        center: map.getBounds().getCenter(),
        maxZoom: 18,
        minZoom: 6,
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="http://mapbox.com">Mapbox</a>',
        id: 'mapbox.streets',
        zoom: 7,
        maxBounds: bounds, // Sets bounds as max
        maxBoundsViscosity: 1.0
    }).addTo(map);

var popup = L.popup();

var lat, lat_goal, lat_start, lon, lon_start, lon_goal;
var id = 0;
var geojsonFeature;
var geoLayer = L.geoJSON(geojsonFeature, {
    onEachFeature: onEachFeature
}).addTo(map);
var goodLayer = L.geoJSON(geojsonFeature, {
    onEachFeature: onEachFeature
}).addTo(map);

function onMapClick(e) {
    lat = e.latlng.lat;
    lon = e.latlng.lng;
    id += 1;
    geojsonFeature = {
        "type": "Feature",
        "id": id,
        "properties": {
            "name": "start",
            "popupContent": "id: " + id + "Lat: " + lat.toFixed(3) + " Lon: " + lon.toFixed(3) + "<br>",
            "start": "<button onclick=start()>start</button>",
            "goal": "<button onclick=goal()>goal</button>",
            "clear_values": "<button onclick=clear_values()>clear</button>",
            "lat": lat,
            "lon": lon
        },
        "geometry": {
            "type": "Point",
            "coordinates": [lon, lat]
        }
    };
    geoLayer.addData(geojsonFeature, {onEachFeature: onEachFeature});
    //L.geoJSON(geojsonFeature, {onEachFeature: onEachFeature}).addTo(map)
}


//sets Start and Goal
//Fehler: brauche lat und lon vom geojson objekt mit dem das popup geöffnet wird
function start() {
    lat_start = lat;
    lon_start = lon;
    geoLayer.removeData(geojsonFeature, {onEachFeature: onEachFeature});
    goodLayer.addData(geojsonFeature, {onEachFeature: onEachFeature});
    info.update();
}

function goal() {
    lat_goal = lat;
    lon_goal = lon;
    goodLayer.addData(geojsonFeature, {onEachFeature: onEachFeature});
    info.update();
}
function clear_values() {
    console.log(id);
    geoLayer.clearLayers();
}

map.on('click', onMapClick);

//Infobox oben rechts
var info = L.control();

info.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
    this.update();
    return this._div;
};

info.update = function (props) {
    this._div.innerHTML = '<h4>Your start and goal</h4>' + '<br> <button onclick=clear_values()>clear</button>' +'<br> Start: ' + lat_start + ', '  + lon_start + '<br> Goal: ' + lat_goal + ', '  + lon_goal;
};

info.addTo(map);

function onEachFeature(feature, layer) {
    // does this feature have a property named popupContent?
    if (feature.properties && feature.properties.popupContent) {
        layer.bindPopup(feature.properties.popupContent + feature.properties.start  + feature.properties.goal);
    }
}