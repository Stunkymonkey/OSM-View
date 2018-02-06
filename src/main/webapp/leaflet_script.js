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
var goal_count = false;
var start_count = false;
var route_count = false;
var lat, lat_goal, lat_start, lon, lon_start, lon_goal, lat_start_short, lat_goal_short, lon_start_short, lon_goal_short;
var geojsonFeature;
var thename;

var greenIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});
var redIcon = new L.Icon({
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});
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
            return L.marker(latlng, {icon: myIcon});
        },
        onEachFeature: function(feature, layer) {
            layerlist[feature.properties.name]=layer;
            if (feature.properties && feature.properties.popupContent) {
                layer.bindPopup(feature.properties.popupContent);
            }
        }
    }).addTo(map);
}

function onMapClick(e){
    lat = e.latlng.lat;
    lon = e.latlng.lng;
    var popup = L.popup()
        .setLatLng(e.latlng)
        .setContent("Lat: " +  e.latlng.lat.toFixed(3) + ", Long: " + e.latlng.lng.toFixed(3) + '<br><button class="btn1" onclick="setStart(lat,lon)" ">Set start</button> '
            + '<button class="btn2" onclick="setGoal(lat,lon)" ">Set Goal</button> ')
        .openOn(map);
}


function setStart(lat,lon) {
    if (thename != undefined && start_count != false) {
        map.removeLayer(layerlist["start"]);
    }
    if (route_count) {
        map.removeLayer(layerlist["line"]);
    }
    start_count = true;
    thename = "start";
    myIcon = redIcon;
    createGeo(lat,lon);
    start(lat,lon);
}
function setGoal(lat,lon) {
    if (thename != undefined && goal_count != false) {
        map.removeLayer(layerlist["goal"]);
    }
    if (route_count) {
        map.removeLayer(layerlist["line"]);
    }
    thename = "goal";
    myIcon = greenIcon;
    goal_count=true;
    createGeo(lat,lon);
    goal(lat,lon);
}
function start(lat,lon) {
    lat_start = lat;
    lon_start = lon;
    lat_start_short = lat.toFixed(3);
    lon_start_short= lon.toFixed(3);
    info.update();
}

function goal(lat,lon) {
    lat_goal = lat;
    lon_goal = lon;
    lat_goal_short = lat.toFixed(3);
    lon_goal_short= lon.toFixed(3);
    info.update();
}
function swapStartGoal() {
    if (goal_count && start_count) {
        var lat_tmp = lat_goal;
        var lon_tmp = lon_goal;
        setGoal(lat_start,lon_start);
        setStart(lat_tmp,lon_tmp);
    }
}

function drawRoute() {
    if (route_count) {
        map.removeLayer(layerlist["line"]);
    }
    route_count = true;
    var myLines = [{
        "type": "LineString",
        "properties": {
            "name": "line"
        },
        "coordinates": [[lon_start, lat_start],[10.6,52.5], [lon_goal,lat_goal]]

    }];
    L.geoJson(myLines, {
        pointToLayer: function (feature, latlng) {
            return L.marker(latlng, {icon: myIcon});
        },
        onEachFeature: function(feature, layer) {
            layerlist[feature.properties.name]=layer;
            if (feature.properties && feature.properties.popupContent) {
                layer.bindPopup(feature.properties.popupContent);
            }
        }
    }).addTo(map);
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
        '<br> <div class="start">Start: </div> ' + lat_start_short + ', '
        + lon_start_short + ' <br> <div class="goal"> Goal:  </div>'+ lat_goal_short + ', ' + lon_goal_short
        + '<br> <button class="btn3"onclick="swapStartGoal()">Swap Start and Goal</button>'
        + '<br> <button class="btn3"onclick="drawRoute()">draw</button>';
};

info.addTo(map);
