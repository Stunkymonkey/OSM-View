var bounds = [
    [47.3, 5.9], // Southwest coordinates
    [54.9, 16.9512215]  // Northeast coordinates
];
var map = new L.map('map').setView([50.5, 9.125], 6);//.setMaxBounds(bounds);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    center: map.getBounds().getCenter(),
    maxZoom: 18,
    minZoom: 2,
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
    '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
    'Imagery © <a href="http://mapbox.com">Mapbox</a>',
    id: 'mapbox.streets',
    zoom: 7,
    maxBoundsViscosity: 1.0
}).addTo(map);

map.doubleClickZoom.disable();
var isGoalDrawn = false;
var isStartDrawn = false;
var isRouteDrawn = false;
var lat, lat_goal, lat_start, lon, lon_start, lon_goal, lat_start_short, lat_goal_short, lon_start_short, lon_goal_short;
var geojsonFeature;
var geoObjectName;
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

var layerlist = {};
map.on('click', onMapClick);
function createGeo(lat, lon) {
    geojsonFeature = {
        "type": "Feature",
        "properties": {
            "name": geoObjectName,
            "popupContent": "Long: " + lon.toFixed(3) + " Lat: " + lat.toFixed(3) + "<br>",
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
//Opens Popup on Mouseclick on the map. (temp) Saves x and y (lon and lat) coordinates of the clicked point.
function onMapClick(e){
    lat = e.latlng.lat;
    lon = e.latlng.lng;
    var popup = L.popup()
        .setLatLng(e.latlng)
        .setContent("Long: " +  e.latlng.lng.toFixed(3) + ", Lat: " + e.latlng.lat.toFixed(3) + '<br><button class="btn1" onclick="setStart(lat,lon)" ">Set start</button> '
            + '<button class="btn2" onclick="setGoal(lat,lon)" ">Set Goal</button> ')
        .openOn(map);
}
//Called on ButtonClick [Set Start] from Popup. Creates geojson object [Start] and adds it to a layerlist. deletes the existing one.
function setStart(lat,lon) {
    if (geoObjectName !== undefined && isStartDrawn !== false) {
        map.removeLayer(layerlist["start"]);
    }
    if (isRouteDrawn) {
        map.removeLayer(layerlist["line"]);
    }
    isStartDrawn = true;
    geoObjectName = "start";
    myIcon = redIcon;
    map.closePopup();
    createGeo(lat,lon);
    start(lat,lon);
}
//Called on ButtonClick [Set Goal] from Popup. Creates geojson object [Goal] and adds it to a layerlist. deletes the existing one.
function setGoal(lat,lon) {
    if (geoObjectName !== undefined && isGoalDrawn !== false) {
        map.removeLayer(layerlist["goal"]);
    }
    if (isRouteDrawn) {
        map.removeLayer(layerlist["line"]);
    }
    geoObjectName = "goal";
    myIcon = greenIcon;
    isGoalDrawn=true;
    map.closePopup();
    createGeo(lat,lon);
    goal(lat,lon);
}
//called from SetStart/SetGoal saves the coordinates. updates the infopannel in the topright corner
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
//Called on ButtonClick [Swap Start and Goal]. Does exactly that.
function swapStartGoal() {
    if (isGoalDrawn && isStartDrawn) {
        var lat_tmp = lat_goal;
        var lon_tmp = lon_goal;
        setGoal(lat_start,lon_start);
        setStart(lat_tmp,lon_tmp);
    }
}
//Called on ButtonClick [Draw Route]. Calls function sendData and deletes the existing Route.
function drawRoute() {
    sendData();
    if (isRouteDrawn) {
        map.removeLayer(layerlist["line"]);
    }
}
//Called from function sendData. Opens Post request for Server. Sends start and goal coordinates. Waits for it to be ready. Receives Route coordinates.
function sendData() {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8081/api/route";
    xhr.open("POST",url,true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            createGeoJsonRoute(json);
        }
    };
    var myObj = {"route": [ [lon_start,lat_start], [lon_goal,lat_goal] ]};
    var data = JSON.stringify(myObj);
	console.log("request: " + data);
    xhr.send(data);
}
//Called from sendData. Creates Geojsonobject for the received Route.
function createGeoJsonRoute(json) {
	console.log("answer: " + json.route);
	if (isGoalDrawn && isStartDrawn) {
        isRouteDrawn = true;
        var myLines = [{
            "type": "LineString",
            "properties": {
                "name": "line"
            },
            "coordinates": json.route

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
	console.log("done painting");
}
//Infopannel in the topright corner.
var info = L.control();
info.onAdd = function (map) {
    this._div = L.DomUtil.create('div', 'info'); // create a div with a class "info"
    L.DomEvent.disableClickPropagation(this._div);
    this.update();
    return this._div;
};
info.update = function (props) {
    this._div.innerHTML = ' <h4>Your start and goal</h4>' +
        '<div class="start">Start: </div> ' + lon_start_short + ', '
        + lat_start_short + ' <br> <div class="goal"> Goal:  </div>'+ lon_goal_short + ', ' + lat_goal_short
        + '<br> <button class="btn3"onclick="swapStartGoal()">Swap Start and Goal</button>'
        + ' <button class="btn3"onclick="drawRoute()">draw Route</button>';
};
info.addTo(map);
