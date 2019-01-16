$( document ).ready(function() {
	
	// first time handle pages
	handlePages();	
	
	// get window height
	var windowHeight = $( window ).height();
	var headerHeight = $('.a').height();
	
	// set grid to window height
	//$('.c').height(windowHeight);
	//$('#map').height(windowHeight-headerHeight);
		
	// submit login page form
	$('#loginForm').on('submit', function(e) {		
		 
		 e.preventDefault(); // do not submit form
		 
		 var id = $('#loginForm input#userid').val();
		 var pass = $('#loginForm input#password').val();
		 
		 // ajax request to check user password will be here
		 var jqxhr = $.get( "/login?id=" + id + "&password=" + pass, function(data) {
			 
			 $.cookie("page", 2); 			 
			 $.cookie("admin", data.isAdmin);
			 
			 handlePages();	
			 
			 var adm = "normalo";
			 
			 if(data.isAdmin){
				 adm = "admin";
				 $('#addBtn').show();
				 //$('#updateBtn').show();
				 $('#deleteBtn').show();
		 	 }
		 
			 $('#helloMsg').text("Hallo " + data.firstname + " " + data.lastname + " - Eingeloggt als " + adm);			 
			 
		 })
	     .fail(function() {
			   $("#loginFailed").text("UserId oder Passwort nicht korrekt!");
		 })	 
	});
	
	// click on persons navigation
	$('ul#navi').on('click', 'li', function(e) {		
		$.cookie("page", 2); 
		
		$(this).parent().children().removeClass('active');
		$(this).addClass('active');
		
		handlePages();		
	});
	
	// click on add button
	$('#addBtn').on('click', function(e) {		
		$.cookie("page", 3); 				
		handlePages();		
		
		$('form #firstname').val("");
		$('form #lastname').val("");
		$('form #address').val("");
		$('form #city').val("");
		$('form #postcode').val("");
	});
	
	// click on update button
	/*$('#updateBtn').on('click', function(e) {		
					
		var id = $("ul#navi li.active").attr("id");
		
		if(id != undefined) {
			
			$.cookie("page", 4); 				
			handlePages();		
			
			var user = getUser(id);
			
			$('form #firstname').val(user.firstname);
			$('form #lastname').val(user.lastname);
			$('form #street').val(user.street);
			$('form #city').val(user.city);
			$('form #postcode').val(user.postcode);
			
		} else {
			
			alert("kein user ausgewählt!");
		}
	});*/
	
	// click on delete button
	$('#deleteBtn').on('click', function(e) {		
		
		var id = $("ul#navi li.active").attr("id");
		var name = $("ul#navi li.active").text();
		
		if(id != undefined) {
			
			if (confirm("User " + name + " löschen?")) {
				  deleteUser(id);
			} 
			
		} else {
			
			alert("Kein user ausgewählt!");
		}
	});
	
	/*
	 * hier habe ich rumgefummelt - Dustin
	 */
	function deleteUser(id){
		
		 var jqxhr = $.get( "/deleteContact?id=" + id, function(data) {
			 $("li#" + id).remove();
		 })
	     .fail(function() {
			   alert( "User konnte nicht gelöscht werden!" );	   
		 })	 
	}
	
	// click on map button
	$('#showMapBtn').on('click', function(e) {	
		
		$.cookie("page", 2); 
		handlePages();
		
		if($('#noUsers').is(':visible')){
			if($.cookie("admin") {
				loadContacts();	
			} else {
				loadPublicContacts();
			}
		}
	});
	
	function loadContacts(data) {
		
		var jqxhr = $.get( "/getAllContacts", function(data) {
			 
			$("#noUsers").hide();
			
			$.each(data, function(k, user) {
				
				getLatLong(user);
								
				/*if($('#OpenLayers_Map_5_OpenLayers_ViewPort').length != 0){
					addNewMarker(user);
				}*/
			});			
		 })
	     .fail(function() {
			   alert( "Keine Kontakte vorhanden!" );	   
		 })	 
	}
	
	function loadPublicContacts(data) {
		
		var jqxhr = $.get( "/getAllPublicContacts", function(data) {
			 
			$("#noUsers").hide();
			
			$.each(data, function(k, user) {
				
				getLatLong(user);
								
				/*if($('#OpenLayers_Map_5_OpenLayers_ViewPort').length != 0){
					addNewMarker(user);
				}*/
			});			
		 })
	     .fail(function() {
			   alert( "Keine Kontakte vorhanden!" );	   
		 })	 
	}
	
	
	// click on logout button
	$('#logoutBtn').on('click', function(e) {		
		$.removeCookie("page"); 
		$.removeCookie("admin");
		handlePages();	
	});
	
	// click on show button
		
	// submit add form
	$('#addForm').on('submit', function(e) {		
		 
		e.preventDefault(); // do not submit form	
		
		var data = {}		
		
		$(this).children().each(function(i,k) {
			
			if($(this).prop("type") == 'text') {
								
				data[$(this).prop("name")] = $(this).val();
			}
			
			if($(this).prop("type") == 'checkbox') {
				
				data[$(this).prop("name")] = $(this).is(':checked');
			}
			
			if($(this).prop("type") == 'select-one') {
				
				data[$(this).prop("name")] = $(this).children('option:selected').text();
			}						
		});
						
		saveData(data);		
	});
	
	function saveData(data) {
		
		 //console.log(data);
		 
		 var url = "/addContact?lastname=" + data.lastname + 
		 			"&firstname=" + data.firstname + 
		 			"&address=" + data.address + 
		 			"&city=" + data.city + 
		 			"&postcode=" + data.postcode + 
		 			"&country=" + data.country
		
		 var jqxhr = $.post(url, function(usr) {
			
			 $.cookie("page", 2); 
			 handlePages();
			 
			 getLatLong(usr);
					
		 })
	     .fail(function() {
			   alert( "Kontakt konnte nicht hinzugefügt werden!" );	   
		 })	 
	}
	
	
	$('#addFormBtn').on('click', function(e) {
		
		$('addForm').submit();
	});
	
	/*$('#updateFormBtn').on('click', function(e) {
			
		$('addForm').submit();
	});*/
	
	$('#deleteFormBtn').on('click', function(e) {
		
		$('addForm').submit();
	});
		
	function getLatLong(user) {
		
		var address = user.address;
		var city = user.city;
		var postcode = user.postcode;
		var os_address = city + "+" + address;
				
		var url= "https://nominatim.openstreetmap.org/search?q=" + os_address + "&format=json&limit=1";
		
		$.get( url, function(data) {
			
			if(data.length){
				
				user.lat = data[0]['lat'];
				user.lon = data[0]['lon'];
								
				addUsertoBar(user);			
				addNewMarker(user);
			
			} else {
				
				alert("address not found");
			}
			
		}).fail(function() {
				
			console.log("error");
		});
	}
	
	function addUsertoBar(user){
		
		$("#noUsers").hide();
		
		console.log(user);
		
		$("ul#navi")
			.append("<li>")
			.children("li")
			.last()
			.text(user.firstname)
			.attr("id",user.id)
			.bind("click", function() {
				//openPopup(user.lon, user.lat);
			});		
	}
	
	function addNewMarker(user) {
		
		var adr = "<b>" + user.firstname + " " + 
					user.lastname + "</b><br />" + 
					user.address + "<br />" + 
					user.postcode + " " + 
					user.city + "<br />" + 
					user.country;
		
		newMarker(user.lon, user.lat, adr);
	}
	
	function handlePages(){
		
		// set page to show cookie: page: 1 = login, 2 = map, 3 = add, 4 = show
		switch ($.cookie('page')) { 
			case '1': 
				$('#loginBox').show();
				$('#mapBox').hide();
				break;
			case '2': 
				$('#loginBox').hide();
				$('#mapBox').show();
				$('#map').show();
				$('#formBox').hide();
				$('#deleteBox').hide();
				
				// draw map only once				
				if($('#OpenLayers_Map_5_OpenLayers_ViewPort').length == 0){
					drawmap();
				}
				break;
			case '3': 
				$('#loginBox').hide();
				$('#mapBox').show();
				$('#map').hide();
				$('#formBox').show();
				$('#addFormBtn').show();
				$('#updateFormBtn').hide();
				$('#deleteBox').hide();
				break;	
			/*case '4': 
				$('#loginBox').hide();
				$('#mapBox').show();
				$('#map').hide();
				$('#formBox').show();
				$('#addFormBtn').hide();
				$('#updateFormBtn').show();
				$('#deleteBox').hide();*/
				break;
			default:
				$.cookie('page', 1); 
				$('#loginBox').show();
				$('#mapBox').hide();
		}
		
		//console.log($.cookie('page'));
	}
});

// Cookie Plugin
/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// CommonJS
		factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (value !== undefined && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setTime(+t + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {};

		// To prevent the for loop in the first place assign an empty array
		// in case there are no cookies at all. Also prevents odd result when
		// calling $.cookie().
		var cookies = document.cookie ? document.cookie.split('; ') : [];

		for (var i = 0, l = cookies.length; i < l; i++) {
			var parts = cookies[i].split('=');
			var name = decode(parts.shift());
			var cookie = parts.join('=');

			if (key && key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		if ($.cookie(key) === undefined) {
			return false;
		}

		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));