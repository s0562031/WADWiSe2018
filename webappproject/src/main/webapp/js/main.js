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
		 
		 // ajax request to check user password will be here
		 
		 
		 // login accepted
		 $.cookie("page", 2); 		 
		 handlePages();	 
	});
	
	// click on persons navigation
	$('ul#navi').on('click', 'li', function(e) {		
		$.cookie("page", 2); 
		
		$(this).parent().children().removeClass('active');
		$(this).addClass('active');
		
		addNewMarker(getUser($(this).attr("id")));
		
		handlePages();		
	});
	
	// click on add button
	$('#addBtn').on('click', function(e) {		
		$.cookie("page", 3); 				
		handlePages();		
		
		$('form #firstname').val("");
		$('form #lastname').val("");
		$('form #street').val("");
		$('form #city').val("");
		$('form #postcode').val("");
	});
	
	// click on update button
	$('#updateBtn').on('click', function(e) {		
					
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
	});
	
	// click on delete button
	$('#deleteBtn').on('click', function(e) {		
		$.cookie("page", 5); 			
		handlePages();	
	});
	
	// click on map button
	$('#showMapBtn').on('click', function(e) {		
		$.cookie("page", 2); 
		handlePages();	
	});
	
	// click on logout button
	$('#logoutBtn').on('click', function(e) {		
		$.removeCookie("page"); 
		handlePages();	
	});
	
	// click on show button
		
	// submit add form
	$('#addForm').on('submit', function(e) {		
		 
		e.preventDefault(); // do not submit form	
		
		var data = {}		
		data['id'] = localStorage.length+1;
		
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
		
		//calling getLatLong --> calls store
		getLatLong(data);
		
	});
	
	$('#addFormBtn').on('click', function(e) {
		
		$('addForm').submit();
	});
	
	$('#updateFormBtn').on('click', function(e) {
			
		$('addForm').submit();
	});
	
	$('#deleteFormBtn').on('click', function(e) {
		
		$('addForm').submit();
	});
	
	function loadLocalStorage() {
		
		for ( var i = 1; i <= localStorage.length; i++ ) {	
			var usr = getUser(i);
			addUsertoBar(usr);
			addNewMarker(usr);
		}
	}
		
	function getUser(id) {
		return JSON.parse(localStorage.getItem(id));
	}
	
	function clearLocalStorage() {
		localStorage.clear();
	}

	function store(id, data) {
		
		if (typeof(Storage) !== "undefined") {
			
		    localStorage.setItem(id, JSON.stringify(data));
		    
		} else alert("undefined");
		
		console.log(id);
	}
	
	function getLatLong(user) {
		
		var street = user.street;
		var city = user.city;
		var postcode = user.postcode;
		var address = city + "+" + street;
				
		var url= "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json&limit=1";
		
		$.get( url, function(data) {
			
			//console.log(data);
			
			if(data.length){
				
				user.lat = data[0]['lat'];
				user.lon = data[0]['lon'];
								
				store(user.id, user);					
				addUsertoBar(user);
				
				$.cookie("page", 2); 
				handlePages();
				
				addNewMarker(user);
			
			} else {
				
				alert("address not found");
			}
			
		}).fail(function() {
				
			console.log("error");
		});
	}
	
	function addUsertoBar(user){
		
		$("ul#navi")
			.append("<li>")
			.children("li")
			.last()
			.text(user.firstname)
			.attr("id",user.id);
	}
	
	function addNewMarker(user) {
		
		console.log(user.lon, user.lat);
		
		var address = user.street + "<br />" + user.postcode + " " + user.city;
		newMarker(user.lon, user.lat, address);
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
					loadLocalStorage();
					//console.log("test");
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
			case '4': 
				$('#loginBox').hide();
				$('#mapBox').show();
				$('#map').hide();
				$('#formBox').show();
				$('#addFormBtn').hide();
				$('#updateFormBtn').show();
				$('#deleteBox').hide();
				break;
			case '5': 
				$('#loginBox').hide();
				$('#mapBox').show();
				$('#map').hide();
				$('#formBox').hide();
				$('#deleteBox').show();
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