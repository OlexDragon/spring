var ipinfo;
$.get('//freegeoip.net/json/', function(data) {
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
					$('<option></option>').val(v.find('countryCode').text()).text(v.find('countryName').text()).attr('geonameId', v.find('geonameId').text()).appendTo($('select.countries'));
				});
		if(ipinfo){
			$('select.countries').val(ipinfo.country_code);
			setRegions($('select.countries').find('option:selected').attr('geonameId'));
		}
	});
	$('select.countries').on('change', function(){
		var selectRegion = $('select.region');
		if(selectRegion){
			setRegions($(this).find('option:selected').attr('geonameId'));
		}
	});
	function setRegions(geonameId){
		$.get('http://api.geonames.org/childrenJSON?geonameId=' + geonameId + '&username=olexdragon', function(data){
			var regions = $(data);
		});		
	}
});