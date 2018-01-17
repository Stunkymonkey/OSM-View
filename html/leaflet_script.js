var bounds = [
    [47.3, 5.9], // Southwest coordinates
    [54.9, 16.9512215]  // Northeast coordinates
];
var map = new L.map('map').setView([50.5, 9.125], 6).setMaxBounds(bounds);

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
map.doubleClickZoom.disable();
var popup = L.popup();
var goal_count = 0;
var start_count = 0;
var lat, lat_goal, lat_start, lon, lon_start, lon_goal, lat_start_short, lat_goal_short, lon_start_short, lon_goal_short;
var geojsonFeature;
var thename;
var redMarker = {
    radius: 8,
    fillColor: "#ff0000"
};
var greenMarker = {
    radius: 8,
    fillColor: "#00ff00"
};
var myMarker;
var layerlist = {};
map.on('click', onMapClick);
function createGeo(lat, lon) {
    geojsonFeature = {
        "type": "Feature",
        "properties": {
            "name": thename,
            "popupContent": "Lat: " + lat.toFixed(3) + " Lon: " + lon.toFixed(3) + "<br>",
            "lat": lat,
            "lon": lon
        },
        "geometry": {
            "type": "Point",
            "coordinates": [lon, lat]
        },

    };
    L.geoJson(geojsonFeature, {
        pointToLayer: function (feature, latlng) {
            return L.circleMarker(latlng, myMarker);
        },
        onEachFeature: function(feature, layer) {
            layerlist[feature.properties.name]=layer;
            if (feature.properties && feature.properties.popupContent) {
                layer.bindPopup(feature.properties.popupContent);
            }
        }
    }).addTo(map);
}
function onButtonClick(e) {
    lat = e.latlng.lat;
    lon = e.latlng.lng;
    createGeo(lat,lon);
    map.off('click', onButtonClick);
    map.on('click', onMapClick);
    if (thename == "start") {
        start();
    } else {
        goal();
    }
}
function onMapClick(e){
    lat = e.latlng.lat;
    lon = e.latlng.lng;
    var popup = L.popup()
        .setLatLng(e.latlng)
        .setContent("Lat: " +  e.latlng.lat.toFixed(3) + ", Long: " + e.latlng.lng.toFixed(3) + '<br><button onclick="setStart(lat,lon)" ">Set start</button> '
            + '<button onclick="setGoal(lat,lon)" ">Set Goal</button> ')
        .openOn(map);
}

function enableAddMarker() {
    map.off('click', onMapClick);
    map.on('click', onButtonClick);
}
function setStart(lat,lon) {
    if (thename != undefined && start_count != 0) {
        map.removeLayer(layerlist["start"]);
    }
    start_count = 1;
    thename = "start";
    myMarker = redMarker;
    createGeo(lat,lon);
    start();
}
function setGoal(lat,lon) {
    if (thename != undefined && goal_count != 0) {
        map.removeLayer(layerlist["goal"]);
    }
    thename = "goal";
    myMarker = greenMarker;
    goal_count=1;
    createGeo(lat,lon);
    goal();
}
function start() {
    lat_start = lat;
    lon_start = lon;
    lat_start_short = lat.toFixed(3);
    lon_start_short= lon.toFixed(3);
    info.update();
}

function goal() {
    lat_goal = lat;
    lon_goal = lon;
    lat_goal_short = lat.toFixed(3);
    lon_goal_short= lon.toFixed(3);
    info.update();
}
function fire_start() {
    if (thename != undefined && start_count != 0) {
        map.removeLayer(layerlist["start"]);
    }
    start_count = 1;
    thename = "start";
    myMarker = redMarker;
    enableAddMarker();

}
function fire_goal() {
    if (thename != undefined && goal_count != 0) {
        map.removeLayer(layerlist["goal"]);
    }
    thename = "goal";
    myMarker = greenMarker;
    goal_count=1;
    enableAddMarker();
}
//Infobox oben rechts
var info = L.control();

info.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
    L.DomEvent.disableClickPropagation(this._div);
    this.update();
    return this._div;
};
info.onRemove = function (map) {
    //L.DomEvent.off();
}

info.update = function (props) {
    this._div.innerHTML = ' <h4>Your start and goal</h4>' +
        '<br><button onclick="fire_start()" ">start</button> ' + lat_start_short + ', '
        + lon_start_short + '<br> <button onclick=fire_goal()>goal</button> ' + lat_goal_short + ', ' + lon_goal_short;
};

info.addTo(map);
