var ipinfo;
$(function() {
	$.get('//freegeoip.net/json/', function(data) {
		ipinfo = data
		$('#city').val(ipinfo.city);

		$.get('/rest/countries', function(data) {
			$.each(data,
					function(index, value) {
						$('<option></option>').val(value.countryCode).text(value.countryName).attr('geonameId', value.geonamesId).appendTo($('select.countries'));
					});
			if(ipinfo){
				$('select.countries').val(ipinfo.country_code);
				var geonameId = $('select#country').find('option:selected').attr('geonameId');
				setRegions(geonameId);
				setPostalCodeFormat(geonameId);
			}
		});
	});
	$('select.countries').on('change', function(){
		var selectRegion = $('select.region');
		var geonameId = $(this).find('option:selected').attr('geonameId');
		if(selectRegion){
			setRegions(geonameId);
		}
		if($(this).attr('id')=='country'){
			setPostalCodeFormat(geonameId);
		}
	});
	function setRegions(geonameId){
		$.get('/rest/regions?geonamesId=' + geonameId,
				function(data){
					if(data){
						var input = $('div.regions input');
						if(input[0]){
							input.replaceWith(input = $('<select></select>').addClass('form-control').attr('name', 'provinceState').prop('required',true));
						}else{
							input = $('div.regions select');
						}
						input.empty();
						$.each(data, function(index, data){
							$('<option></option>').val(data.regionCode).text(data.regionName).attr('geonameId', data.geonameId).appendTo(input);
						});
						input.val(ipinfo.region_code);
					}else{
						var input = $('div.regions select');
						if(input[0]){
							input.replaceWith(input = $('<input></input>').addClass('form-control').attr('type', 'text').attr('id', 'postalCode').attr('name', 'postalCode').prop('required',true));
						}
					}
		});
	}
	function setPostalCodeFormat(geonameId){
		$.get('/rest/country?geonamesId='+geonameId, function(country){
			$('input#postalCode').attr('postalCodeFormat', country.postalCodeFormat);
		});
	}
});