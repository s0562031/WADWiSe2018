var map;
var layer_mapnik;
var layer_tah;
var layer_markers;

function drawmap() {

	OpenLayers.Lang.setCode('de');
   
    // Position und Zoomstufe der Karte - Berlin
    var lon = 13.404954;
    var lat = 52.520008;
    var zoom = 11;

    map = new OpenLayers.Map('map', {
        projection: new OpenLayers.Projection("EPSG:900913"),
        displayProjection: new OpenLayers.Projection("EPSG:4326"),
        controls: [
            new OpenLayers.Control.Navigation(),
            new OpenLayers.Control.LayerSwitcher(),
            new OpenLayers.Control.PanZoomBar()],
        maxExtent:
            new OpenLayers.Bounds(-20037508.34,-20037508.34,
                                   20037508.34, 20037508.34),
        numZoomLevels: 18,
        maxResolution: 156543,
        units: 'meters'
    });

    layer_mapnik = new OpenLayers.Layer.OSM.Mapnik("Mapnik");
    layer_markers = new OpenLayers.Layer.Markers("Address", { 
    		projection: new OpenLayers.Projection("EPSG:4326"), 
   	    	visibility: true, displayInLayerSwitcher: false });

    map.addLayers([layer_mapnik, layer_markers]);
    jumpTo(lon, lat, zoom);
}

function newMarker(lon, lat, popuptext) {
	
   var lonLat = new OpenLayers.LonLat(lon,lat)
          .transform(
            new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
            map.getProjectionObject() // to Spherical Mercator Projection
          );
          
    var zoom=10;
    var markers = new OpenLayers.Layer.Markers( "Markers" );    
    
    // dynamisch
    addMarker(layer_markers, Number(lon), Number(lat), popuptext);
    map.setCenter (lonLat, zoom);
}

function jumpTo(lon, lat, zoom) {
    var x = Lon2Merc(lon);
    var y = Lat2Merc(lat);
    map.setCenter(new OpenLayers.LonLat(x, y), zoom);
    return false;
}

function Lon2Merc(lon) {
    return 20037508.34 * lon / 180;
}

function Lat2Merc(lat) {
    var PI = 3.14159265358979323846;
    lat = Math.log(Math.tan( (90 + lat) * PI / 360)) / (PI / 180);
    return 20037508.34 * lat / 180;
}

function addMarker(layer, lon, lat, popupContentHTML) {

	var ll = new OpenLayers.LonLat(Lon2Merc(lon), Lat2Merc(lat));
    var feature = new OpenLayers.Feature(layer, ll); 
    feature.closeBox = true;
    feature.popupClass = OpenLayers.Class(
    		OpenLayers.Popup.FramedCloud, {minSize: new OpenLayers.Size(300, 180) } );
    feature.data.popupContentHTML = popupContentHTML;
    feature.data.overflow = "hidden";
    
    //console.log(feature);
    
    var marker = new OpenLayers.Marker(ll);
    marker.feature = feature;

    var markerClick = function(evt) {
        if (this.popup == null) {
            this.popup = this.createPopup(this.closeBox);
            map.addPopup(this.popup);
            this.popup.show();
        } else {
            this.popup.toggle();
        }
        OpenLayers.Event.stop(evt);
    };
    marker.events.register("mousedown", feature, markerClick);
   
    layer.addMarker(marker);
    map.addPopup(feature.createPopup(feature.closeBox));
   // popup.hide();
}

function openPopup(lon, lat){
	var popups = map.popups;
		
	for(i=0; i<popups.length; i++){
		var plon = popups[i]['lonlat']['lon'];
		var plat = popups[i]['lonlat']['lat'];
		
		//console.log(plon, plat);
	}
}

function getCycleTileURL(bounds) {
   var res = this.map.getResolution();
   var x = Math.round((bounds.left - this.maxExtent.left) / (res * this.tileSize.w));
   var y = Math.round((this.maxExtent.top - bounds.top) / (res * this.tileSize.h));
   var z = this.map.getZoom();
   var limit = Math.pow(2, z);

   if (y < 0 || y >= limit)
   {
     return null;
   }
   else
   {
     x = ((x % limit) + limit) % limit;

     return this.url + z + "/" + x + "/" + y + "." + this.type;
   }
}
