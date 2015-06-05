var ipinfo;
$.get('//freegeoip.net/json/', function(data) {
	ipinfo = data
	$('#city').val(ipinfo.city);
	$('select.countries').val(ipinfo.country_code);
});
$(function() {
	$.get('http://api.geonames.org/countryInfo?username=olexdragon', function(data) {
		var sortedCountries = $(data).find('geonames').find('country').get().sort(function(a, b) {
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
			var regions = data.geonames;
			if(regions[0]){
				var input = $('div.regions input');
				if(input[0]){
					input.replaceWith(input = $('<select></select>').addClass('form-control').attr('name', 'provinceState').prop('required',true));
				}else{
					input = $('div.regions select');
				}
				$.each(regions, function(index, data){
					$('<option></option>').val(data.adminName1).text(data.adminName1).appendTo(input);
				});
				input.val(ipinfo.region_name);
			}
		});
	}
});