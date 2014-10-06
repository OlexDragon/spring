package jk.web.workers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jk.web.html.select.ContentDiv;

import org.springframework.beans.factory.annotation.Autowired;

public class SearchClass {

	public enum SearchDetails{
		ALL,
		CONTACTS,
		COMPANY
	}

	@Autowired
	private UserWorker userWorker;

	@NotNull
	@Size(max = 64)
	private String searchFor;

	private SearchDetails searchDetails;

	public String getSearchFor() {
		return searchFor;
	}
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	public SearchDetails getSearchDetails() {
		return searchDetails;
	}
	public void setSearchDetails(SearchDetails searchDetails) {
		this.searchDetails = searchDetails;
	}

	public ContentDiv getContentDivs() {
		ContentDiv contentDiv = new ContentDiv();
		contentDiv.setContent(searchFor);
		return contentDiv;
	}

	@Override
	public String toString() {
		return "SearchClass [searchFor=" + searchFor + ", searchDetails=" + searchDetails + "]";
	}
}
