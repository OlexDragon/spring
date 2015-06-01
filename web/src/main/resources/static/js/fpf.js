var ipinfo;
$.get("http://ipinfo.io", function(data) {
	ipinfo = data
});
$(function() {
	$.get('http://api.geonames.org/countryInfo?username=olexdragon', function(
			data) {
		var sortedCountries = $(data).find('geonames').find('country').get()
				.sort(function(a, b) {
					var valA = $(a).find('countryName').text();
					var valB = $(b).find('countryName').text();
					return valA < valB ? -1 : valA == valB ? 0 : 1;
				});
		$.each(sortedCountries,
				function(index, value) {
					var v = $(value);
					$('<option></option>').val(v.find('countryCode').text())
							.text(v.find('countryName').text()).attr(
									'geonameId', v.find('geonameId').text())
							.appendTo($('select.countries'));
				});
		if(ipinfo){
			
		}
	});
});