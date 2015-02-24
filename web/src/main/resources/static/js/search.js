var page;
$(function() {
	$('#searchForm').on('submit', function(e) {

		e.preventDefault();
		var keywords = $('#keywords').val();
		if (keywords) {
			keywords = $.trim(keywords);
			if (keywords) {
				var data = {};
				data['keywords'] = keywords;

				var location = $('#location').val();
				if (location) {
					location = $.trim(location);
					if (location.length != 0) {
						data['location'] = location;
					}
				}

				var page = $('#page').val();
				if (page) {
					data['page'] = page;
				}

				$.ajax({
					url : '/careerjet',
					type : 'POST',
					data : data,

					success : function(data) {
						$('#jobs').remove();
						$('#srch_rslt> li').remove();
						switch (data.type) {
						case "JOBS":
							jobs(data);
							break;
						case "LOCATIONS":
							locations(data.solveLocations);
							break;
						case "ERROR":
							alert(data);
							break;
						default:
							alert(data);
						}
					}
				});
			}
		}
		;
	});
	function jobs(data) {
		jobsCount(data.hits);
		jobsPages(data.pages);
		jobsResults(data.jobs);
	}
	function jobsCount(hits) {
		if (hits !== undefined) {
			$('#srch_count').attr('name', 'count').text(hits + " results");
		}
	}
	function jobsPages(pages) {
		if (pages !== undefined) {
			if (pages > 1) {
				for ( var i = 1; i <= pages; i++) {
					var tag = $('#srch_rslt').attr('name', 'pages');
					var li = $('<li></li>').append('<a href="#">' + i + '</a>')
							.appendTo(tag);
					var page = $('#page').val();
					if ((page && page == i) || (!page && i == 1)) {
						li.addClass("selected");
					}
				}
				$('#page').val('');
				$('ul[name="pages"] a').on('click', function(e) {
					e.preventDefault();
					$('#page').val(this.text);
					$('#searchForm').submit();
				});
			}
		}
	}
	function jobsResults(jobs) {
		if (jobs) {
			var length = jobs.length;
			if (length > 0) {
				var list = $('<ul></ul>').attr('id', 'jobs').insertAfter(
						'#srch_rslt');

				$.each(jobs, function(index, value) {

					var li = $('<li></li>').appendTo(list);
					li.append('<a href="' + value.url + '" target="_blank">'
							+ (value.company ? value.company : 'Go to site')
							+ '</a>');
					var sList = $('<ul></ul>').appendTo(li);
					if (value.title) {
						$('<li></li>').appendTo(sList).append(
								'<span>Title: </span>' + value.title);
					}
					if (value.salary) {
						$('<li></li>').appendTo(sList).append(
								'<span>Salary: </span>' + value.salary);
					}
					if (value.salary_currency_code) {
						$('<li></li>').appendTo(sList).append(
								'<span>Salary currency code: </span>'
										+ value.salary_currency_code);
					}
					if (value.locations) {
						$('<li></li>').appendTo(sList).append(
								'<span>Locations: </span>' + value.locations);
					}
					if (value.description) {
						$('<li></li>').appendTo(sList).append(
								'<span>Description: </span>'
										+ value.description);
					}
					var site = value.site;
					if (site) {
						if (site.substr(0, 3) != 'http') {
							site = 'http://' + site;
						}
						$('<li></li>').appendTo(sList).append(
								'<span>Site: </span><a href="' + site
										+ '" target="_blank">' + value.site
										+ '</a>');
					}
				});
			}
		}
	}
	function locations(solveLocations) {
		if (solveLocations && solveLocations.length > 0) {
			$('#srch_count').attr('name', 'solveLocations').text(
					'Select Location');
			var loc = $('#srch_rslt').attr('name', 'locations');
			$.each(solveLocations, function(index, value) {
				$('<li></li>').appendTo(loc).append(
						'<a href="#">' + value.name + '</a>');
			});
			$('ul[name="locations"] a').on('click', function(e) {
				e.preventDefault();
				$('#location').val(this.text);
				$('#searchForm').submit();
			});
		} else
			alert('Impossible to find a location');
	}
});
$('.search a').on(
		'click',
		function(e) {

			e.preventDefault();
			$('#srch_count').empty();
			$('#srch_rslt> li').remove();
			$.ajax({
				url : '/search/' + this.text,
				type : 'POST',

				success : function(data) {
					$.each(data, function(index, value) {
						if (value.categoryId == 0) {
							$('#srch_count').text(value.categoryName);
						} else {
							var caregories = $('#srch_rslt').attr('name',
									'categories');
							$('<li></li>').append(
									'<a href="#">' + value.categoryName
											+ "</a>").appendTo(caregories);
						}
					});
					$('ul[name="categories"] a').on('click', function(e) {
						e.preventDefault();
						$('#keywords').val(this.text);
						$('#searchForm').submit();
					});
				}
			});
		});
