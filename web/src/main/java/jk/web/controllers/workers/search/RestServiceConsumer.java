package jk.web.controllers.workers.search;

import jk.web.data.beans.search.job.interfaces.SearchDetails;

import org.springframework.data.domain.Page;
import org.springframework.web.client.RestTemplate;

public class RestServiceConsumer implements Runnable{

	public RestServiceConsumer(SearchDetails searchDetails,String keywords, String location, Integer page, Integer pagesize) {
		searchDetails.setKeywords(keywords);
		searchDetails.setLocation(location);
		searchDetails.setPage(page);
		searchDetails.setPagesize(pagesize);
	}

	@Override
	public void run() {
		RestTemplate restTemplate = new RestTemplate();
		Page page = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class);
	}

}
